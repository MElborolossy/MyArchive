package utils;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static utils.Connections.myConn;

public class MYSQL {
    // ***** Check if Database is Exist Function ***** \\
    public static int isDBExist(String dbName) {
        try {
            Statement myState = myConn.createStatement();
            String sql = "SHOW databases LIKE '" + dbName + "'";
            ResultSet myRs = myState.executeQuery(sql);
            if (myRs.first()) {
                myState.closeOnCompletion();
                return 1;
            } else {
                myState.closeOnCompletion();
                return 0;
            }
        } catch (SQLException e) {
            System.out.println("SQLException:: " + e.getErrorCode());
            return 0;
        }
    }

    // ***** Create database with all tables ***** \\
    public static void dbCreate(String dbName) {
        try {
            Statement myState = myConn.createStatement();
            String sql = "CREATE DATABASE " + dbName;
            myState.executeUpdate(sql);

            sql = "use " + dbName;
            myState.executeUpdate(sql);
            sql = "create table archivefax ( date DATE, subject VARCHAR(500), faxfrom VARCHAR(500), faxto VARCHAR(500), faxid VARCHAR(500), archiveid VARCHAR(500) not null"
                    + ", action VARCHAR(500), notes VARCHAR(500), link VARCHAR(500), follow VARCHAR(500), followstatus TINYINT(1),"
                    + "faxdate DATE, directedbranch VARCHAR(500), primary key (archiveid) )";
            myState.executeUpdate(sql);

            sql = "use " + dbName;
            myState.executeUpdate(sql);
            sql = "create table externalbranches ( id smallint unsigned not null auto_increment, name VARCHAR(500), primary key (id) )";
            myState.executeUpdate(sql);

            sql = "use " + dbName;
            myState.executeUpdate(sql);
            sql = "create table internalbranches ( id smallint unsigned not null auto_increment, name VARCHAR(500), primary key (id) )";
            myState.executeUpdate(sql);

            sql = "use " + dbName;
            myState.executeUpdate(sql);
            sql = "create table printers ( id smallint unsigned not null auto_increment, name VARCHAR(500), defaultP VARCHAR(40), primary key (id) )";
            myState.executeUpdate(sql);

            sql = "use " + dbName;
            myState.executeUpdate(sql);
            sql = "create table scanner ( id smallint unsigned not null, programloc VARCHAR(500), pc VARCHAR(100), primary key (id) )";
            myState.executeUpdate(sql);
            myState.closeOnCompletion();
        } catch (SQLException e) {
            System.out.println("SQLException:: " + e.getErrorCode());
        }
    }

    // ***** Get Table Result ***** \\
    public static ResultSet dbGetTableResult(String tableName) {
        try {
            Statement myState = myConn.createStatement();
            String sql = "select * from " + tableName;
            ResultSet myRs = myState.executeQuery(sql);
            myState.closeOnCompletion();
            return myRs;
        } catch (SQLException e) {
            System.out.println("SQLException:: " + e.getErrorCode());
            return null;
        }
    }

    // ***** Insert Fax  ***** \\
    public static void dbInsertValues(String tableName, String[] headerName, String[] value) {
        try {
            Statement myState = myConn.createStatement();
            String sql = "insert into " + tableName;
            String names = " (";
            String values = " values (";
            for (int i = 0; i < value.length; i++) {
                if (i == value.length - 1) {
                    names += headerName[i] + ")";
                    values += "'" + value[i] + "')";
                } else {
                    names += headerName[i] + ", ";
                    values += "'" + value[i] + "', ";
                }
            }
            sql += names + values;
            myState.execute(sql);
            myState.closeOnCompletion();
        } catch (SQLException e) {
            System.out.println("SQLException:: " + e.getErrorCode());
        }
    }

    // ***** Insert certain value using AutoIncrement ***** \\
    public static void dbInsertValueAI(String tableName, String inputCat, String input) {
        try {
            Statement myState = myConn.createStatement();
            String sql = "insert into " + tableName + " (" + inputCat + ") value" + "('" + input + "')";
            myState.execute(sql);
            myState.closeOnCompletion();
        } catch (SQLException e) {
            System.out.println("SQLException:: " + e.getErrorCode());
        }
    }

    // ***** Search Inside Table using any header ***** \\
    public static ResultSet dbSearch(String tableName, String searchSection, String searchWord) {
        try {
            Statement myState = myConn.createStatement();
            String sql = "select * from " + tableName + " where " + searchSection + " like '%" + searchWord + "%'";
            ResultSet myRs = myState.executeQuery(sql);
            myState.closeOnCompletion();
            return myRs;
        } catch (SQLException e) {
            System.out.println("SQLException:: " + e.getErrorCode());
            return null;
        }
    }

    // ***** Search inside Table using primary key ***** \\
    public static ResultSet dbSearchPK(String tableName, String searchWord) {
        try {
            Statement myState = myConn.createStatement();
            String sql = "select * from " + tableName + " where archiveid = '" + searchWord + "'";
            ResultSet myRs = myState.executeQuery(sql);
            myState.closeOnCompletion();
            return myRs;
        } catch (SQLException e) {
            System.out.println("SQLException:: " + e.getErrorCode());
            return null;
        }
    }

    // ***** Update certain value using PrimaryKey ***** \\ -- TO BE EDITED --
    public static void dbUpdateValues(String tableName, String pkColName, String pk, String[] updateSection, String[] updateValue) {
        try {
            Statement myState = myConn.createStatement();
            String sql = "update " + tableName + " set ";
            for (int i = 0; i < updateSection.length; i++) {
                if (i == updateSection.length - 1) {
                    sql += updateSection[i] + " ='" + updateValue[i] + "' where " + pkColName + " ='" + pk + "'";
                } else {
                    sql += updateSection[i] + " ='" + updateValue[i] + "', ";
                }
            }
            myState.execute(sql);
            myState.closeOnCompletion();
        } catch (SQLException e) {
            System.out.println("SQLException:: " + e.getErrorCode());
        }
    }

    // ***** Delete certain value using PrimaryKey for AutoIncrement Tables ***** \\
    public static void dbDeleteValueAI(String tableName, String pkColName, String pk) {
        try {
            Statement myState = myConn.createStatement();
            String sql = "delete from " + tableName + " where " + pkColName + " ='" + pk + "'";
            myState.execute(sql);
            sql = "update " + tableName + " Set " + pkColName + " =  " + pkColName + "- 1 where " + pkColName + " > " + pk;
            myState.execute(sql);
            myState.closeOnCompletion();
        } catch (SQLException e) {
            System.out.println("SQLException:: " + e.getErrorCode());
        }
    }

    // ***** Delete certain value using PrimaryKey for AutoIncrement Tables ***** \\
    public static void dbDeleteValue(String tableName, String pkColName, String pk) {
        try {
            Statement myState = myConn.createStatement();
            String sql = "delete from " + tableName + " where " + pkColName + " ='" + pk + "'";
            myState.execute(sql);
            myState.closeOnCompletion();
        } catch (SQLException e) {
            System.out.println("SQLException:: " + e.getErrorCode());
        }
    }

    // ***** Backup Database as SQL File Function ***** \\
    public static void dbSQLBackup(String dbUser, String dbPass, String dbName) {
        try {
            /*NOTE: Getting path to the Jar file being executed*/
            CodeSource codeSource = MYSQL.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            String jarDir = jarFile.getParentFile().getPath();

            /*NOTE: Creating Path Constraints for folder saving*/
            /*NOTE: Here the backup folder is created for saving inside it*/
            String folderPath = jarDir + "\\backup";

            /*NOTE: Creating Folder if it does not exist*/
            File backupFolder = new File(folderPath);
            backupFolder.mkdir();

            /*NOTE: Creating Path Constraints for backup saving*/
            /*NOTE: Here the backup is saved in a folder called backup with the name backup.sql*/
            String savePath = "\"" + jarDir + "\\backup\\" + "backup.sql\"";

            /* Create Backup Batch File */
            File backupBatFile = new File(folderPath + "\\BackupBatch.bat");
            FileWriter writeBackup = new FileWriter(backupBatFile);
            PrintWriter printBackup = new PrintWriter(writeBackup);
            printBackup.printf("%s" + "%n", "@echo off");
            printBackup.printf("%s" + "%n", "\"C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe\" -u" + dbUser
                    + " -p" + dbPass + " -B " + dbName + " >" + savePath);
            printBackup.printf("%s" + "%n", "exit 0");
            printBackup.close();

            /*NOTE: Used to create a cmd command*/
            String executeCmd = "cmd /c start /B \"\" " + folderPath + "\\BackupBatch.bat";
            /*NOTE: Executing the command here*/
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();
            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
            if (processComplete == 0) {
                System.out.println("Backup Complete");
            } else {
                System.out.println("Backup Failed");
            }

        } catch (URISyntaxException | IOException | InterruptedException ex) {
            JOptionPane.showMessageDialog(null, "Error at Backup \n" + ex.getMessage());
        }
    }

    // Restore Database from SQL File \\
    public static void dbSQLRestore(String dbUser, String dbPass, String dbName) {
        try {
            /*NOTE: Getting path to the Jar file being executed*/
            CodeSource codeSource = MYSQL.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            String jarDir = jarFile.getParentFile().getPath();

            /*NOTE: Creating Path Constraints for folder saving*/
            /*NOTE: Here the backup folder is created for saving inside it*/
            String folderPath = jarDir + "\\backup";

            /*NOTE: Creating Folder if it does not exist*/
            File restoreFolder = new File(folderPath);
            restoreFolder.mkdir();

            // SQL Backup file
            String savePath = "\"" + jarDir + "\\backup\\" + "backup.sql\"";

            /* Create Restore Batch File */
            File restoreBatFile = new File(folderPath + "\\RestoreBatch.bat");
            FileWriter writeRestore = new FileWriter(restoreBatFile);
            PrintWriter printRestore = new PrintWriter(writeRestore);
            printRestore.printf("%s" + "%n", "@echo off");
            printRestore.printf("%s" + "%n",
                    "\"C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysql.exe\" -u" + dbUser + " -p" + dbPass
                            + " -B " + dbName + " <" + savePath);
            printRestore.printf("%s" + "%n", "exit 0");
            printRestore.close();

            /*NOTE: Used to create a cmd command*/
            String executeCmd = "cmd /c start \"\" " + folderPath + "\\RestoreBatch.bat";
            // NOTE: Executing the command here *\
            Process runtimeProcess;
            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();
            if (processComplete == 0) {
                System.out.println("Restore Complete");
            } else {
                System.out.println("Restore Failed");
            }
        } catch (IOException | InterruptedException | URISyntaxException e) {
            JOptionPane.showMessageDialog(null, "Error at Restore Database \n" + e.getMessage());
            e.printStackTrace();
        }
    }

    // Get ResultSet Length \\
    public static int dbResultSetLength(ResultSet myRs) {
        int len;
        try {
            if (myRs.last()) {
                len = myRs.getRow();
                myRs.first();
                return len;
            } else return 0;
        } catch (SQLException e) {
            System.out.println("SQLException:: " + e.getErrorCode());
            return -1;
        }
    }
}
