package utils;

import guiUtils.GridBagLabel;
import guiUtils.GridBagPanel;
import pages.*;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static utils.Connections.myConn;
import static utils.ConstantAttributes.*;

public class CommonMethods {

    // *** Load Internal and External branches from DB Function *** \\
    public static void loadBranches(DefaultListModel<String> branchList, String type) {
        String sqlBranches = "select * from " + type;
        try {
            Statement myStat = myConn.createStatement();
            branchList.clear();
            ResultSet myRs = myStat.executeQuery(sqlBranches);
            while (myRs.next()) {
                branchList.addElement(myRs.getString("name"));
            }
            myStat.closeOnCompletion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // *** Set Horizontal Scroll Bar to the start *** \\
    public static void setHScrollBar(JScrollPane pane) {
        Timer timer = new Timer(10, arg0 -> pane.getHorizontalScrollBar().setValue(0));
        timer.setRepeats(false); // Only execute once
        timer.start(); // Go go go!
    }

    // *** Check Null String *** \\
    public static String checkNull(String str) {
        if (str == null) str = "";
        return str;
    }

    // Go To Page Function
    public static GridBagPanel createPage(String pageName) {
        switch (pageName) {
            case "add":
                return new AddPage(new int[]{20, 200, 0, 150, 200, 0, 150, 20, 0},
                        new double[]{0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE},
                        new int[]{20, 0, 10, 30, 5, 30, 5, 50, 5, 70, 5, 50, 5, 30, 5, 20, 5, 10, 5, 0},
                        new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                                0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE});
            case "edit":
                return new EditPage(new int[]{20, 150, 100, 150, 150, 100, 150, 20, 0},
                        new double[]{0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE},
                        new int[]{20, 40, 20, 100, 0, 5, 30, 5, 50, 5, 50, 5, 0, 5, 10, 5, 10, 10, 0},
                        new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                                0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE});
            case "search":
                return new SearchPage(new int[]{20, 150, 150, 75, 75, 150, 150, 20, 0},
                        new double[]{0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE},
                        new int[]{20, 30, 30, 20, 0, 20, 0, 30, 0},
                        new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE});
            case "follow":
                return new FollowPage(new int[]{20, 0, 0, 0, 0, 20, 0},
                        new double[]{0.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE},
                        new int[]{30, 0, 30, 0, 30, 0, 30, 0},
                        new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE});
            case "options":
            case "option":
                return new OptionsPage(new int[]{10, 200, 10, 0},
                        new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE},
                        new int[]{20, 0, 20, 0, 20, 0, 20, 0, 0, 0},
                        new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE});
            case "branches":
            case "branch":
                return new BranchPage(new int[]{20, 0, 20, 0, 20, 0, 50, 0, 0, 0, 0, 0, 20, 0},
                        new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0,
                                Double.MIN_VALUE}, new int[]{20, 30, 10, 0, 10, 30, 10, 30, 20, 0},
                        new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE});
            default:
                return new MainPage(mainBackgroundImage, new int[]{20, 60, 150, 200, 150, 60, 0},
                        new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE},
                        new int[]{20, 0, 251, 30, 30, 30, 75, 0},
                        new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE});
        }
    }

    /*
    // ** Check Saved Options Function ** \\
        * errorCounter == 0 --> no errors \\
        * printer not found +1 -- scanner not found +2 \\
        * errorCounter == 1 --> printer not found only \\
        * errorCounter == 2 --> scanner not found only \\
        * errorCounter == 3 --> both not found \\
     */
    public static int loadSavedOptions(GridBagLabel defPrintL, GridBagLabel scannerPath) {
        ResultSet myRs;
        int errorCounter = 0;
        try {
            myRs = MYSQL.dbSearch(PRINTERSTABLE, "defaultP", "1");
            if (myRs.first()) {
                defaultPrinter = myRs.getString("name");
                defPrintL.setText(defaultPrinter);
            } else {
                JOptionPane.showOptionDialog(null, "الرجاء الإنتهاء من إعدادات الطابعة", "خطأ في الإعدادات",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                errorCounter = errorCounter + 1;
            }
            myRs = MYSQL.dbGetTableResult(SCANNERTABLE);
            if (myRs.first()) {
                scannerLink = myRs.getString("programloc");
                scannerPath.setText(scannerLink);
            } else {
                JOptionPane.showOptionDialog(null, "الرجاء الإنتهاء من إعدادات الماسح الضوئي", "خطأ في الإعدادات",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                errorCounter = errorCounter + 2;
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return errorCounter;
    }

    public static FileWriter createFileWriter(File file){
        try {
            return new FileWriter(file,true);
        } catch (IOException handled) {
            JOptionPane.showMessageDialog(null,"غير قادر على إنشاء الملف","حطأ",JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public static File createInternalFile(String folderName, String fileName){
        try {
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            String jarDir = jarFile.getParentFile().getPath();
            String folderPath = jarDir + "\\" + folderName;
            File folder = new File(folderPath);
            if(!folder.exists()){
                folder.mkdir();
            }
            File file = new  File(folderPath + "\\" + fileName);
            return file;
        } catch (URISyntaxException handled) {
            JOptionPane.showMessageDialog(null,"غير قادر على إنشاء الملف","حطأ",JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
