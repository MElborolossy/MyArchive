package utils;

import guiUtils.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static main.MyArchive.*;
import static utils.CommonMethods.checkNull;
import static utils.Connections.*;
import static utils.ConstantAttributes.*;
import static utils.MYSQL.dbResultSetLength;


public class CommonButtons {
    // Text Actions
    public static final Action copyAction = new CopyTextAction();
    public static final Action pasteAction = new PasteTextAction();

    // Back to Home Page Action
    public static void backToHome(GridBagImageButton button) {
        button.addActionListener(arg0 -> {
            mainPage.centerPanel(layeredPane);
            if(isConnected){
                mainPage.checkFollow();
            }
        });
    }

    // Print pdf file
    public static void printPdfFile(GridBagImageButton button, GridBagTable table) {
        // Print Button Action
        button.addActionListener(arg0 -> {
            int selectedRow = table.getSelectedRow();
            String filePath;
            if (optionsStatus == 1 || optionsStatus == 3) {
                JOptionPane.showOptionDialog(null, "لا يوجد طابعة افتراضية", "خطأ في الإعدادات",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                optionsPage.centerPanel(layeredPane);
            } else if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "رجاءًا اختر فاكس");
            } else {
                try {
                    ResultSet myRs = MYSQL.dbSearchPK(MAINTABLENAME, table.getValueAt(selectedRow, 9).toString());
                    if (myRs.first()) {
                        filePath = myRs.getString("link");
                        filePath = filePath.replace("/", "\\");
                        filePath = filePath.replace("#", "\\");
                        if (filePath.contains(".pdf")) {
//                            if (!isServer) {
//                                filePath = openFileFromClient(serverPCName, filePath);
//                                PrintPdf.print(filePath, clientDefaultPrinter);
//                            }else{
                            PrintPdf.print(filePath, defaultPrinter);
//                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "رجاءًا اختر ملف بصيغة pdf");
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "خطأ في قاعدة البيانات");
                    }
                } catch (SQLException | IOException | URISyntaxException e) {
                    if(e instanceof SQLException){
                        JOptionPane.showMessageDialog(null, "خطأ في قاعدة البيانات");
                    }
                    if(e instanceof IOException){
                        JOptionPane.showMessageDialog(null, "الرجاء التأكد من الرابط");
                    }
                    if(e instanceof URISyntaxException){
                        JOptionPane.showMessageDialog(null, "خطأ في الطباعة");
                    }
                    e.printStackTrace();
                }
            }
        });
    }

    // Open PDF or Image File & Folders
    public static void openLink(GridBagImageButton button, GridBagTable table) {
        button.addActionListener(arg0 -> {
            String filePath;
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "رجاءًا اختر فاكس");
            } else {
                try {
                    ResultSet myRs = MYSQL.dbSearchPK(MAINTABLENAME, table.getValueAt(selectedRow, 9).toString());
                    if (myRs.first()) {
                        filePath = myRs.getString("link");
                        filePath = filePath.replace("/", "\\");
                        filePath = filePath.replace("#", "\\");
//                        if (!isServer) {
//                            filePath = openFileFromClient(serverPCName, filePath);
//                        }
                        Desktop.getDesktop().open(new File(filePath));
                    }
                } catch (SQLException | IOException e) {
                    if(e instanceof SQLException){
                        JOptionPane.showMessageDialog(null, "خطأ في قاعدة البيانات");
                    }
                    if(e instanceof IOException){
                        JOptionPane.showMessageDialog(null, "الرجاء التأكد من الرابط");
                    }
                    e.printStackTrace();
                }
            }
        });
    }

    public static void openFoldersLink(GridBagImageButton button, GridBagTable table){
        button.addActionListener(arg0 -> {
            String filePath;
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "رجاءًا اختر فاكس");
            } else {
                try {
                    ResultSet myRs = MYSQL.dbSearchPK(MAINTABLENAME, table.getValueAt(selectedRow, 9).toString());
                    if (myRs.first()) {
                        filePath = myRs.getString("link");
                        filePath = filePath.replace("/", "\\");
                        filePath = filePath.replace("#", "\\");
//                        if (!isServer) {
//                            filePath = openFileFromClient(serverPCName, filePath);
//                        }
                        Desktop.getDesktop().open(new File(filePath).getParentFile());
                    }
                } catch (SQLException | IOException e) {
                    if(e instanceof SQLException){
                        JOptionPane.showMessageDialog(null, "خطأ في قاعدة البيانات");
                    }
                    if(e instanceof IOException){
                        JOptionPane.showMessageDialog(null, "الرجاء التأكد من الرابط");
                    }
                    e.printStackTrace();
                }
            }
        });
    }

    public static void chooseLink(GridBagImageButton button, GridBagTextField linkField){
        button.addActionListener(arg0 -> {
            chooseFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            chooseFile.showOpenDialog(null);
            String absPath = chooseFile.getSelectedFile().getAbsolutePath();
            String modAbsPath = "";
            for (int i = 0; i < absPath.length(); i++) {
                if (absPath.charAt(i) == '\\') {
                    modAbsPath += '/';
                } else {
                    modAbsPath += absPath.charAt(i);
                }
            }
            linkField.setText(modAbsPath);
        });
    }

    public static void findFaxes(GridBagImageButton button, GridBagTable table, GridBagComboBox combo,
                                 GridBagDateChooser date, GridBagTextField word){
        button.addActionListener(arg0 -> {
            table.getTableModel().setRowCount(0);
            String searchCat = Categories.values()[combo.getSelectedIndex()].toString();
            String sql;
            String searchWord;
            if (searchCat.equals("date") || searchCat.equals("faxdate")) {
                // ### Get Date ###
                searchWord = df.format(date.getDate());
                sql = "select * from " + MAINTABLENAME + " where " + searchCat + " like '" + searchWord + "'";
            } else {
                searchWord = word.getText();
                sql = "select * from " + MAINTABLENAME + " where " + searchCat + " like '%" + searchWord + "%'";
            }
            try {
                Statement myStat = myConn.createStatement();
                ResultSet myRs = myStat.executeQuery(sql);
                while (myRs.next()) {
                    String dateStr = checkNull(myRs.getString("date"));
                    String faxDate = checkNull(myRs.getString("faxdate"));
                    String subj = checkNull(myRs.getString("subject"));
                    String from = checkNull(myRs.getString("faxfrom"));
                    String to = checkNull(myRs.getString("faxto"));
                    String faxId = checkNull(myRs.getString("faxid"));
                    String archiveid = checkNull(myRs.getString("archiveid"));
                    String action = checkNull(myRs.getString("action"));
                    String notes = checkNull(myRs.getString("notes"));
                    String follow = checkNull(myRs.getString("follow"));
                    String directB = checkNull(myRs.getString("directedbranch"));

                    Object[] searchRow = new Object[]{notes, follow, action, directB, faxId, faxDate, to, from,
                            subj, archiveid, dateStr};
                    table.getTableModel().addRow(searchRow);
                }
                int myRsLength = dbResultSetLength(myRs);
                table.resizeColumnWidth();
                table.centerTable();
                JOptionPane.showMessageDialog(null, "تم العثور على " + myRsLength + " فاكس");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
    }
}
