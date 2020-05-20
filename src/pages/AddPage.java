package pages;

import guiUtils.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static main.MyArchive.*;
import static utils.CommonButtons.*;
import static utils.CommonMethods.loadBranches;
import static utils.CommonMethods.setHScrollBar;
import static utils.Connections.isConnected;
import static utils.ConstantAttributes.*;
import static utils.MYSQL.*;

public class AddPage extends GridBagPanel {
    // Add page components
    private boolean isLoaded = false;
    // Table
    private final GridBagTable aTable = new GridBagTable(headers, 1, 1);

    // Date Choosers
    private final GridBagDateChooser aDate = new GridBagDateChooser(1, 3);
    private final GridBagDateChooser aFaxDate = new GridBagDateChooser(1, 5);

    // Labels
    private final GridBagLabel aDateLabel = new GridBagLabel(lDateIcon, SwingConstants.CENTER, GridBagConstraints.VERTICAL, 3, 3);
    private final GridBagLabel aDateFaxLabel = new GridBagLabel(lDateFaxIcon, SwingConstants.CENTER, GridBagConstraints.VERTICAL, 3, 5);
    private final GridBagLabel aDirectListLabel = new GridBagLabel(lDirectedIcon, SwingConstants.CENTER, GridBagConstraints.VERTICAL, 3, 7);
    private final GridBagLabel aToLabel = new GridBagLabel(lToIcon, SwingConstants.CENTER, GridBagConstraints.VERTICAL, 3, 9);
    private final GridBagLabel aActionLabel = new GridBagLabel(lActionIcon, SwingConstants.CENTER, GridBagConstraints.VERTICAL, 3, 11);
    private final GridBagLabel aNoteLabel = new GridBagLabel(lNoteIcon, SwingConstants.CENTER, GridBagConstraints.VERTICAL, 3, 13);

    private final GridBagLabel aArchiveLabel = new GridBagLabel(lArchiveIcon, SwingConstants.CENTER, GridBagConstraints.VERTICAL, 6, 3);
    private final GridBagLabel aFaxIDLabel = new GridBagLabel(lFaxIcon, SwingConstants.CENTER, GridBagConstraints.VERTICAL, 6, 5);
    private final GridBagLabel aSubjLabel = new GridBagLabel(lSubjIcon, SwingConstants.CENTER, GridBagConstraints.VERTICAL, 6, 7);
    private final GridBagLabel aFromLabel = new GridBagLabel(lFromIcon, SwingConstants.CENTER, GridBagConstraints.VERTICAL, 6, 9);
    private final GridBagLabel aFollowLabel = new GridBagLabel(lFollowIcon, SwingConstants.CENTER, GridBagConstraints.VERTICAL, 6, 11);
    private final GridBagLabel aFileLabel = new GridBagLabel(lFileIcon, SwingConstants.CENTER, GridBagConstraints.VERTICAL, 6, 13);

    // TextFields
    private final GridBagTextField aActionText = new GridBagTextField(1, 11);
    private final GridBagTextField aNoteText = new GridBagTextField(1, 13);
    private final GridBagTextField aArchiveIDText = new GridBagTextField(4, 3);
    private final GridBagTextField aFaxIDText = new GridBagTextField(4, 5);
    private final GridBagTextField aSubjText = new GridBagTextField(4, 7);
    private final GridBagTextField aFileText = new GridBagTextField(SwingConstants.LEFT, 4, 13);

    // CheckBoxList
    private final GridBagCheckBoxList aDirectList = new GridBagCheckBoxList(1, 7);
    private final GridBagCheckBoxList aToList = new GridBagCheckBoxList(1, 9);
    private final GridBagCheckBoxList aFromList = new GridBagCheckBoxList(4, 9);
    private final GridBagCheckBoxList aFollowList = new GridBagCheckBoxList(4, 11);

    // Buttons
    private final GridBagImageButton aScanButton = new GridBagImageButton(scanIcon, 1, 15);
    private final GridBagImageButton aAddButton = new GridBagImageButton(addFaxIcon, 3, 15);
    private final GridBagImageButton aFileButton = new GridBagImageButton(addLinkIcon, 4, 15);
    private final GridBagImageButton aBackButton = new GridBagImageButton(homeIcon, 6, 15);
    private final GridBagImageButton aPrintButton = new GridBagImageButton(printIcon, 1, 17);
    private final GridBagImageButton aPasteButton = new GridBagImageButton(pasteAction, pasteIcon, 3, 17);
    private final GridBagImageButton aCopyButton = new GridBagImageButton(copyAction, copyIcon, 4, 17);
    private final GridBagImageButton aBranchButton = new GridBagImageButton(branchesIcon, 6, 17);

    public AddPage(int[] colWidth, double[] colWeights, int[] rowHeight, double[] rowWeights) {
        super(colWidth, colWeights, rowHeight, rowWeights);
        loadPage();
    }

    @Override
    public void loadPage() {
        this.add(aTable.getTableScrollPane(), aTable.getGBC());
        this.add(aDate, aDate.getGBC());
        this.add(aFaxDate, aFaxDate.getGBC());
        this.add(aDateLabel, aDateLabel.getGBC());
        this.add(aDateFaxLabel, aDateFaxLabel.getGBC());
        this.add(aArchiveLabel, aArchiveLabel.getGBC());
        this.add(aFaxIDLabel, aFaxIDLabel.getGBC());
        this.add(aDirectListLabel, aDirectListLabel.getGBC());
        this.add(aSubjLabel, aSubjLabel.getGBC());
        this.add(aToLabel, aToLabel.getGBC());
        this.add(aFromLabel, aFromLabel.getGBC());
        this.add(aActionLabel, aActionLabel.getGBC());
        this.add(aFollowLabel, aFollowLabel.getGBC());
        this.add(aNoteLabel, aNoteLabel.getGBC());
        this.add(aFileLabel, aFileLabel.getGBC());
        this.add(aArchiveIDText, aArchiveIDText.getGBC());
        this.add(aFaxIDText, aFaxIDText.getGBC());
        this.add(aSubjText, aSubjText.getGBC());
        this.add(aActionText, aActionText.getGBC());
        this.add(aNoteText, aNoteText.getGBC());
        this.add(aFileText, aFileText.getGBC());
        this.add(aDirectList.getScrollPane(), aDirectList.getGBC());
        this.add(aToList.getScrollPane(), aToList.getGBC());
        this.add(aFromList.getScrollPane(), aFromList.getGBC());
        this.add(aFollowList.getScrollPane(), aFollowList.getGBC());
        this.add(aScanButton, aScanButton.getGBC());
        this.add(aAddButton, aAddButton.getGBC());
        this.add(aFileButton, aFileButton.getGBC());
        this.add(aBackButton, aBackButton.getGBC());
        this.add(aPrintButton, aPrintButton.getGBC());
        this.add(aPasteButton, aPasteButton.getGBC());
        this.add(aCopyButton, aCopyButton.getGBC());
        this.add(aBranchButton, aBranchButton.getGBC());
        if(isConnected){
            // Load Branches in CheckBoxLists
            loadComboBoxLists();
            // Reset Horizontal Scroll Bar to Start
            setHScrollBarPage();
        }
        onClickButtonsListeners();

    }

    @Override
    public void centerPanel(JLayeredPane layer) {
        super.centerPanel(layeredPane);
        if (!isLoaded) {
            setHScrollBarPage();
            isLoaded = true;
        }
    }

    @Override
    public void reloadPage() {
        super.reloadPage();
        loadComboBoxLists();
        setHScrollBarPage();
    }

    public void onClickButtonsListeners() {
        onClickBranchButton();
        onClickScanButton();
        onClickAddButton();
        printPdfFile(aPrintButton, aTable);
        backToHome(aBackButton);
        chooseLink(aFileButton,aFileText);
    }

    public void loadComboBoxLists() {
        loadBranches(aDirectList.getCheckBoxListModel(), INTERNALTABLE);
        loadBranches(aToList.getCheckBoxListModel(), EXTERNALTABLE);
        loadBranches(aFromList.getCheckBoxListModel(), EXTERNALTABLE);
        loadBranches(aFollowList.getCheckBoxListModel(), INTERNALTABLE);
    }

    public void setHScrollBarPage() {
        setHScrollBar(aDirectList.getScrollPane());
        setHScrollBar(aToList.getScrollPane());
        setHScrollBar(aFromList.getScrollPane());
        setHScrollBar(aFollowList.getScrollPane());
    }

    private void onClickBranchButton() {
        aBranchButton.addActionListener(arg0 -> {
            branchPage.centerPanel(layeredPane);
        });
    }

    private void onClickScanButton() {
        aScanButton.addActionListener(arg0 -> {
            if (optionsStatus >= 2) {
                JOptionPane.showOptionDialog(null, "برنامج الماسح الضوئي غير موجود", "خطأ في الإعدادات",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                optionsPage.centerPanel(layeredPane);
            } else {
                scannerLink = scannerLink.replace("#", "\\");
                scannerLink = scannerLink.replace("/", "\\");
                try {
                    Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + scannerLink);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "خطأ في تشغيل برنامج الماسح الضوئي",
                            "الرجاء ضبط الإعدادات", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        });
    }

    private void onClickAddButton(){
        aAddButton.addActionListener(arg0 -> {
            String faxFromStr = aFromList.getCheckedValues();
            String faxToStr = aToList.getCheckedValues();
            String followStr = aFollowList.getCheckedValues();
            String directStr = aDirectList.getCheckedValues();
            String filePathStr = aFileText.getText();
            String followStatus = "";
            String dateStr = "";
            String faxDateStr = "";
            String archiveIDStr = "";
            String[] insertHeaders = {"date", "faxdate", "subject", "faxfrom", "faxto", "faxid", "archiveid",
                    "action", "notes", "link", "directedbranch", "follow", "followstatus"};
            Statement myStat;
            Object[] tableRow;

            // Follow ComboBox method
            if (followStr.equals("لا يوجد") || followStr.equals("")) {
                followStatus = "0";
            } else {
                followStatus = "1";
            }

            // ### Get Date ###
            if (aDate.getDate() != null && aFaxDate.getDate() != null) {
                faxDateStr = df.format(aFaxDate.getDate());
                dateStr = df.format(aDate.getDate());
            }

            // Check ArchiveId contains "بدون" and get the new ID
            if (aArchiveIDText.getText().equals("بدون")) {
                archiveIDStr = newWithoutID();
            } else {
                archiveIDStr = aArchiveIDText.getText();
            }

            /* Check if the program running from host or other connected computer
             * To edit the file path
            if (!isServer) {
                filePathStr = saveFileFromClient(serverPCName, aFileText);
            }
             */

            String[] insertValues = {dateStr, faxDateStr, aSubjText.getText(), faxFromStr, faxToStr,
                    aFaxIDText.getText(), archiveIDStr, aActionText.getText(), aNoteText.getText(), filePathStr,
                    directStr, followStr, followStatus};

            try {
                ResultSet myRs = dbSearchPK(MAINTABLENAME, archiveIDStr);
                if(myRs.first()){
                    JOptionPane.showMessageDialog(null, "رقم الأرشيف المكرر" + "\n" + "الرجاء اختيار رقم آخر");
                }else if (dateStr.equals("")){
                    JOptionPane.showMessageDialog(null,  "لم يتم إدخال التاريخ" + "\n" + "الرجاء إدخال التاريخ ");
                }else {
                    dbInsertValues(MAINTABLENAME, insertHeaders, insertValues);
                    tableRow = new Object[]{aNoteText.getText(), followStr, aActionText.getText(), directStr,
                            aFaxIDText.getText(), faxDateStr, faxToStr, faxFromStr, aSubjText.getText(),
                            archiveIDStr, dateStr};
                    aTable.getTableModel().addRow(tableRow);
                    JOptionPane.showMessageDialog(null, "تم إضافة الفاكس بنجاح");
                    resetAllFields();
                    aTable.resizeColumnWidth();
                    aTable.centerTable();
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });
    }

    private void resetAllFields(){
        aSubjText.setText("");
        aNoteText.setText("");
        aFaxIDText.setText("");
        aArchiveIDText.setText("");
        aActionText.setText("");
        aFileText.setText("");

        int[] ind = {-1};
        aFromList.setCheckBoxListSelectedIndices(ind);
        aToList.setCheckBoxListSelectedIndices(ind);
        aFollowList.setCheckBoxListSelectedIndices(ind);
        aDirectList.setCheckBoxListSelectedIndices(ind);
        aFaxDate.cleanup();
        aDate.cleanup();
    }

    // *** Add Fax without archive id Algorithm *** \\
    private static String newWithoutID() {
        String yearStr = JOptionPane.showInputDialog(null, "أدخل سنة المكاتبة");
        String searchStr = "بدون - " + yearStr;
        ResultSet myRs = dbSearch(MAINTABLENAME,"archiveid", searchStr);
        int myRsLength = dbResultSetLength(myRs);
        String newWithoutNumber = Integer.toString(myRsLength + 1);
        return searchStr + " [" + newWithoutNumber + "]";
    }
}
