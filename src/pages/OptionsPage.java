package pages;

import guiUtils.*;
import utils.PrintPdf;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import static main.MyArchive.connObject;
import static utils.CommonButtons.backToHome;
import static utils.CommonMethods.loadSavedOptions;
import static utils.Connections.connectionInfoData;
import static utils.Connections.isConnected;
import static utils.ConstantAttributes.*;
import static utils.MYSQL.*;

public class OptionsPage extends GridBagPanel {
    // Options Page Components
    // Panels
    private final GridBagTitledPanel oPrinterPanel = new GridBagTitledPanel("إعدادات الطابعة", 1, 1,
            new int[]{0, 0, 0, 0, 0, 150, 0, 0},
            new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE}, new int[]{10, 0, 30, 10, 0},
            new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE});
    private final GridBagTitledPanel oScannerPanel = new GridBagTitledPanel("إعدادات الماسح الضوئي", 1, 3,
            new int[]{0, 0, 0, 0, 0, 0, 150, 0, 0},
            new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE}, new int[]{10, 30, 10, 0},
            new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE});
    private final GridBagTitledPanel oBackupPanel = new GridBagTitledPanel("إعدادات الاتصال و النسخ الاحتياطي", 1, 5,
            new int[]{0, 0, 30, 10, 30, 0}, new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE},
            new int[]{10, 0, 10, 0}, new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE});

    // Labels
    private final GridBagLabel oPrinterLabel = new GridBagLabel(lDefPrinterIcon, SwingConstants.CENTER,
            GridBagConstraints.BOTH, 5, 1);
    private final GridBagLabel oDefPrinterLabel = new GridBagLabel("", SwingConstants.CENTER, GridBagConstraints.BOTH, 3, 2);
    private final GridBagLabel oScannerLabel = new GridBagLabel(lScannerIcon, SwingConstants.CENTER, GridBagConstraints.BOTH,
            6, 1);
    private final GridBagLabel oDefScannerLabel = new GridBagLabel("", SwingConstants.CENTER, GridBagConstraints.BOTH, 4, 1);
    private final GridBagLabel oCopyRightsLabel = new GridBagLabel(copyrightsIcon, 2, GridBagConstraints.EAST,
            GridBagConstraints.VERTICAL, 1, 8);

    // ComboBoxes
    private final GridBagComboBox oPrintersCombo = new GridBagComboBox(new String[]{""}, 3, 1);

    // Buttons
    private final GridBagImageButton oPrinterButton = new GridBagImageButton(defaultPrinterIcon, 1, 1);
    private final GridBagImageButton oScannerButton = new GridBagImageButton(scanIcon, 2, 1);
    private final GridBagImageButton oBackupButton = new GridBagImageButton(backupIcon, 1, 1);
    private final GridBagImageButton oRestoreButton = new GridBagImageButton(restoreIcon, 3, 1);
    private final GridBagImageButton oNewDBButton = new GridBagImageButton(newDBIcon, 1, 2);
    private final GridBagImageButton oConnectionButton = new GridBagImageButton(connectionSettingsIcon, 3, 2);
    private final GridBagImageButton oBackButton = new GridBagImageButton(homeIcon, 1, 7);

    public OptionsPage(int[] colWidth, double[] colWeights, int[] rowHeight, double[] rowWeights) {
        super(colWidth, colWeights, rowHeight, rowWeights);
        loadPage();
    }

    @Override
    public void loadPage() {
        this.add(oPrinterPanel, oPrinterPanel.getGBC());
        this.add(oScannerPanel, oScannerPanel.getGBC());
        this.add(oBackupPanel, oBackupPanel.getGBC());
        this.add(oBackButton, oBackButton.getGBC());
        this.add(oCopyRightsLabel, oCopyRightsLabel.getGBC());
        oPrinterPanel.add(oPrinterLabel, oPrinterLabel.getGBC());
        oPrinterPanel.add(oDefPrinterLabel, oDefPrinterLabel.getGBC());
        oPrinterPanel.add(oPrintersCombo, oPrintersCombo.getGBC());
        oPrinterPanel.add(oPrinterButton, oPrinterButton.getGBC());
        oScannerPanel.add(oScannerLabel, oScannerLabel.getGBC());
        oScannerPanel.add(oDefScannerLabel, oDefScannerLabel.getGBC());
        oScannerPanel.add(oScannerButton, oScannerButton.getGBC());
        oBackupPanel.add(oBackupButton, oBackupButton.getGBC());
        oBackupPanel.add(oRestoreButton, oRestoreButton.getGBC());
        oBackupPanel.add(oNewDBButton,oNewDBButton.getGBC());
        oBackupPanel.add(oConnectionButton,oConnectionButton.getGBC());

        onClickButtonsListeners();
        if(isConnected){
            loadPrinterCombo();
        }
    }

    public void onClickButtonsListeners() {
        backToHome(oBackButton);
        onClickPrinterButton();
        onClickScannerButton();
        onClickBackupButton();
        onClickRestoreButton();
        onClickConnectButton();
        onClickCreateDBButton();
    }

    public int getLoadOptionsStatus() {
        return loadSavedOptions(oDefPrinterLabel, oDefScannerLabel);
    }

    private void loadPrinterCombo() {
        String[] printerList = PrintPdf.listPrinters();
//        if(isServer){
        for (String printer : printerList) {
            ResultSet myRs = dbSearch(PRINTERSTABLE,"name", printer);
            if (dbResultSetLength(myRs) <= 0) { // No Result Found
                dbInsertValueAI(PRINTERSTABLE,"name", printer);
            }
            oPrintersCombo.addItem(printer);
        }
//        } else { // List the Printers that only in the DB
//            Statement myStat = null;
//            try {
//                myStat = myConn.createStatement();
//                ResultSet myRs = MYSQL.getTableResult(myStat, PRINTERSTABLE);
//                while (myRs.next()) {
//                    oPrintersCombo.addItem(myRs.getString("name"));
//                }
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//        }
    }

    private void onClickPrinterButton() {
        oPrinterButton.addActionListener(arg0 -> {
            try {
                ResultSet myRs = dbSearch(PRINTERSTABLE,"defaultP","1");
                String[] updateSections = {"defaultP"};
                String[] updateValues;
                if (myRs.first()) {
                    String id = myRs.getString("id");
                    updateValues = new String[]{"0"};
                    dbUpdateValues(PRINTERSTABLE, "id", id, updateSections, updateValues);
                }
                defaultPrinter = oPrintersCombo.getSelectedItem().toString();
                oDefPrinterLabel.setText(defaultPrinter);
                updateValues = new String[]{"1"};
                dbUpdateValues(PRINTERSTABLE, "name", defaultPrinter, updateSections, updateValues);
                if (optionsStatus == 1 || optionsStatus == 3){
                    optionsStatus --;
                }
                myRs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void onClickScannerButton(){
        oScannerButton.addActionListener(arg0 -> {
            chooseFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
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
            scannerLink = modAbsPath;
            oDefScannerLabel.setText(modAbsPath);
            try {
                ResultSet myRs = dbGetTableResult(SCANNERTABLE);
                if(myRs.first()){
                    String[] updateSections = {"programloc"};
                    String[] updateValues = new String[]{modAbsPath};
                    dbUpdateValues(SCANNERTABLE, "id", "1", updateSections, updateValues);
                }else{
                    String[] headers = {"id","programloc","pc"};
                    String[] insertValues = {"1",modAbsPath,""};
                    dbInsertValues(SCANNERTABLE, headers,insertValues );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void onClickBackupButton(){
        oBackupButton.addActionListener(arg0 -> {
            dbSQLBackup(connectionInfoData.username,connectionInfoData.password,connectionInfoData.databaseName);
        });
    }

    private void onClickRestoreButton(){
        oRestoreButton.addActionListener(arg0 -> {
            dbSQLRestore(connectionInfoData.username,connectionInfoData.password,connectionInfoData.databaseName);
        });
    }

    private void onClickConnectButton(){
        oConnectionButton.addActionListener(arg0 -> {
            connObject.newConnect();
        });
    }
    private void onClickCreateDBButton(){
        oNewDBButton.addActionListener(arg0 -> {
            dbCreate(connectionInfoData.databaseName);
        });
    }
}

