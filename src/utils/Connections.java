package utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pages.ConnectionPanel;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

import static utils.CommonMethods.createInternalFile;

public class Connections {
    // Connection Attributes
    public static Connection myConn;
    private static final JsonHelper jsonHelper = new JsonHelper();
    private static File connectionFile = null ;
    public static ConnectionInfo connectionInfoData = null;
    public ConnectionPanel connectPanel = new ConnectionPanel();
    public static boolean isServer = false;
    public static boolean isConnected = false;

    private static String localhostName = "";
    private static String localhostIP = "";
    private static List<DiscoverNetworkDevice> networkDevices = new ArrayList();

    // Initializations
    public Connections(){
        List<String> host = getHostInfo();
        localhostIP = host.get(0);
        localhostName = host.get(1);
        generateNetworkDevices();
        connectionFile = createInternalFile("ConnectionInfo", "info.json");
        try {
            connectionInfoData = jsonHelper.readConnectionInfoData(connectionFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static List<String> getHostInfo() {
        ArrayList<String> hostInfo = new ArrayList();
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
            hostInfo.add(addr.getHostAddress());
            hostInfo.add(addr.getHostName());
        } catch (UnknownHostException e) {
            System.out.println("Error to get Host Information");
            e.printStackTrace();
        }
        return hostInfo;
    }
    private static void generateNetworkDevices(){
        String local = localhostIP.substring(0,localhostIP.lastIndexOf(".") + 1);
        for (int i = 1; i <= 254; i++) {
            // Assuming IPV4
            networkDevices.add(new DiscoverNetworkDevice(local + i));
        }
    }

    public static void setDefaultValues(){
        connectionInfoData.server = "";
        connectionInfoData.port = "";
        connectionInfoData.databaseName = "";
        connectionInfoData.username = "";
        connectionInfoData.password = "";
        jsonHelper.writeConnectionInfoData(connectionInfoData);
    }

    public void newConnect(){
        connectPanel.getConnectionInfoFirstTime();
        jsonHelper.writeConnectionInfoData(connectionInfoData);
        connectLocal();
        MYSQL.useDBIfExist(connectionInfoData);
    }

    public void dbConnect(){
        if (connectionInfoData.server.length() <= 0) {
            connectPanel.getConnectionInfoFirstTime();
            jsonHelper.writeConnectionInfoData(connectionInfoData);
        }
        connectLocal();
    }


    private void connectLocal() {
        String connectServer = "";
        if(connectionInfoData.server.equals(localhostIP)) {
            connectServer = "jdbc:mysql://localhost:" + connectionInfoData.port;
        }else{
            connectServer = "jdbc:mysql://"+connectionInfoData.server + ":" + connectionInfoData.port;
        }
        // First case connect with connection info from info file or JOptionPane Gathering data
        if (connect(connectServer)) { // First case connect with connection info from info file or JOptionPane Gathering data
            if(connectionInfoData.server.contains(localhostIP)) isServer = true;
            isConnected = true;
        }else{ // Second case try to connect by searching the local IPs in same network
            try {
                // Error Pane
                JOptionPane connectionErrorPane = new JOptionPane();
                JDialog connectionErrorDialog = connectionErrorPane.createDialog(null, "محاولة التوصيل");
                Thread t = new Thread(() -> {
                    connectionErrorPane.setMessage("المدخلات غير صحيحة"+ "\n يتم محاولة البحث عن قاعدة البيانات في الشبكة المحلية" + "\n تستغرق بعض الوقت الرجاء الانتظار");
                    connectionErrorPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
                    connectionErrorPane.setOptions(new Object[]{});
                    connectionErrorPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    connectionErrorDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                    connectionErrorDialog.pack();
                    connectionErrorDialog.setVisible(true);
                });
                t.start();
                ForkJoinPool myPool = new ForkJoinPool(16);
                Object discoveredDevices = myPool.submit(() -> networkDevices
                        .parallelStream()
                        .filter(nd -> nd.Discover()).findFirst()).get();
                if(discoveredDevices.toString().contains("empty")) { // If there is no local server running
                    isConnected = false;
                }else { // Success to connect locally
                    isConnected = true;
                    jsonHelper.writeConnectionInfoData(connectionInfoData); // Update data in info file
                }
                connectionErrorDialog.dispose();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
    private static boolean connect(String server) {
        try {
            myConn = DriverManager.getConnection(server,connectionInfoData.username,connectionInfoData.password);
            System.out.println(server + " Success to Connect");
            return true;
        } catch (SQLException handled) {
            System.out.println(server + " Fail to Connect");
            return false;
        }
    }

    private static class DiscoverNetworkDevice {
        private String hostIp;
        private String hostName;

        public DiscoverNetworkDevice(String hostIp) {
            this.hostIp = hostIp;
        }

        public boolean Discover() {
            String connectServer ;
            try {
                InetAddress host = InetAddress.getByName(hostIp);
                if (host.isReachable(200)) {
                    if (hostIp.equals(localhostIP)) connectServer = "jdbc:mysql://localhost:" + connectionInfoData.port;
                    else connectServer = "jdbc:mysql://" + hostIp + ":" + connectionInfoData.port;

                    if (connect(connectServer)) { // Check connect using reachable id
                        connectionInfoData.server = hostIp;
                        hostName = host.getHostName();
                        if (hostName.equals(localhostName)) isServer = true;
                        return true;
                    }
                }
            } catch (IOException ignored) {
            }
            return false;
        }

        @Override
        public String toString() {
            return String.format("IP: %s \t Name: %s", hostIp, hostName);
        }
    }

    private static class JsonHelper {
        private JSONParser jsonParser;
        private JSONArray jsonArray;

        public ConnectionInfo readConnectionInfoData(String filePath) throws IOException {
            jsonParser= new JSONParser();
            FileReader reader = null;
            Object obj;
            try {
                reader = new FileReader(filePath);
                jsonArray = new JSONArray();
                obj = jsonParser.parse(reader);
                reader.close();
            } catch (FileNotFoundException e) {
                reader.close();
                System.out.println("File not found.");
                e.printStackTrace();
                return new ConnectionInfo();
            } catch (IOException | ParseException e) {
                reader.close();
                System.out.println("The file is not correctly formatted");
                e.printStackTrace();
                return new ConnectionInfo();
            }
            ConnectionInfo connectionInfoInput = parseInfoObject((JSONObject) obj);
            return connectionInfoInput;
        }

        public void writeConnectionInfoData(ConnectionInfo connectionInfo){
            FileWriter fileWriter = null;
            if(connectionFile.delete()){
                try {
                    connectionFile.createNewFile();
                    fileWriter =  new FileWriter(connectionFile,true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            JSONObject connInfo = new JSONObject();
            connInfo.put("Server", connectionInfo.server);
            connInfo.put("Port", connectionInfo.port);
            connInfo.put("Database", connectionInfo.databaseName);
            connInfo.put("Username", connectionInfo.username);
            connInfo.put("Password", connectionInfo.password);

            JSONObject connObject = new JSONObject();
            connObject.put("Info",connInfo);

//            jsonArray = new JSONArray();
//            jsonArray.add(connObject);
            try {
                fileWriter.write(connObject.toJSONString());
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private ConnectionInfo parseInfoObject(JSONObject info) {
            //Get info object within list
            JSONObject userObject = (JSONObject) info.get("Info");
            //Get info first name
            String server = (String) userObject.get("Server");

            //Get info last name
            String port = (String) userObject.get("Port");

            //Get info mobile
            String database = (String) userObject.get("Database");

            //Get info email
            String username = (String) userObject.get("Username");

            //Get info password
            String password = (String) userObject.get("Password");

            return new ConnectionInfo(server,port,database,username,password);
        }

    }
    public static class ConnectionInfo {
        public String server;
        public String port;
        public String databaseName;
        public String username;
        public String password;

        public ConnectionInfo(){
            this.server = "";
            this.port = "";
            this.databaseName = "";
            this.username = "";
            this.password = "";
        }
        public ConnectionInfo(String server, String port, String databaseName, String username, String password) {
            this.server = server;
            this.port = port;
            this.databaseName = databaseName;
            this.username = username;
            this.password = password;
        }
    }


}

//    // ** Open File From Client Method ** \\
//    private String openFileFromClient(String serverName, String filePath) {
//        serverName = serverName.substring(0, 1).toUpperCase() + serverName.substring(1).toLowerCase();
//        if (filePath.contains(":")) {
//            filePath = filePath.replaceAll(":", "");
//        }
//        filePath = "\\" + "\\" + serverName + "\\" + filePath.substring(0, 1).toLowerCase() + filePath.substring(1);
//        return filePath;
//    }
//
//    // ** Save File From Client Method ** \\
//    private String saveFileFromClient(String serverName, String filePath) {
//        serverName = serverName.substring(0, 1).toUpperCase() + serverName.substring(1).toLowerCase();
//        if (filePath.contains(serverName)) {
//            filePath = filePath.replaceAll(serverName, "");
//        }
//        filePath = filePath.substring(3);
//        filePath = filePath.substring(0, 1).toUpperCase() + ":" + filePath.substring(1);
//        return filePath;
//    }

// Remote Connections
//    public static String SERVERNAME = "jdbc:mysql://remotemysql.com:3306/"; // for remotely connection
//    public static String DBNAME = "45cMD8VQlT";
//    public static final String USERNAME = "45cMD8VQlT";
//    public static final String PASSWORD = "b9qFpHh1xP";