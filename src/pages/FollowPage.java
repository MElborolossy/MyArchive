package pages;

import guiUtils.GridBagImageButton;
import guiUtils.GridBagLabel;
import guiUtils.GridBagPanel;
import guiUtils.GridBagTable;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static utils.CommonButtons.*;
import static utils.CommonMethods.*;
import static utils.Connections.myConn;
import static utils.ConstantAttributes.*;
import static utils.MYSQL.*;

public class FollowPage extends GridBagPanel {
    // Follow Page Components
    // Tables
    private GridBagTable fTable = new GridBagTable(headers, 4, 1, 1);

    // Labels
    private GridBagLabel fQuesLabel = new GridBagLabel(lFollowQIcon, 2,GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
            3, 3);

    // Buttons
    private GridBagImageButton fNoButton = new GridBagImageButton(noIcon, 1, 3);
    private GridBagImageButton fYesButton = new GridBagImageButton(yesIcon, 2, 3);
    private GridBagImageButton fPrintButton = new GridBagImageButton(printIcon, 1, 5);
    private GridBagImageButton fOpenPDFButton = new GridBagImageButton(pdfIcon, 2, 5);
    private GridBagImageButton fCopyButton = new GridBagImageButton(copyAction, copyIcon, 3, 5);
    private GridBagImageButton fBackButton = new GridBagImageButton(homeIcon, 4, 5);

    public FollowPage(int[] colWidth, double[] colWeights, int[] rowHeight, double[] rowWeights) {
        super(colWidth, colWeights, rowHeight, rowWeights);
        loadPage();
    }

    @Override
    public void loadPage() {
        this.add(fTable.getTableScrollPane(), fTable.getGBC());
        this.add(fQuesLabel, fQuesLabel.getGBC());
        this.add(fNoButton, fNoButton.getGBC());
        this.add(fYesButton, fYesButton.getGBC());
        this.add(fPrintButton, fPrintButton.getGBC());
        this.add(fOpenPDFButton, fOpenPDFButton.getGBC());
        this.add(fCopyButton, fCopyButton.getGBC());
        this.add(fBackButton, fBackButton.getGBC());

        onClickButtonsListeners();
    }

    public void onClickButtonsListeners() {
        backToHome(fBackButton);
        printPdfFile(fPrintButton, fTable);
        openLink(fOpenPDFButton, fTable);
        onClickYesButton();
    }

    public void loadFollowFaxes(){
        String sql = "select * from " + MAINTABLENAME + " where followstatus like '1'";
        fTable.getTableModel().setRowCount(0);
        try {
            Statement myStat = myConn.createStatement();
            ResultSet myRs = myStat.executeQuery(sql);
            int myRsLength = dbResultSetLength(myRs);
            if (myRsLength == 0) {
                JOptionPane.showMessageDialog(null, "لا يوجد فاكسات للمتابعة");
            }else {
                myRs.first();
                do{
                    String date = checkNull(myRs.getString("date"));
                    String faxDate = checkNull(myRs.getString("faxdate"));
                    String subj = checkNull(myRs.getString("subject"));
                    String from = checkNull(myRs.getString("faxfrom"));
                    String to = checkNull(myRs.getString("faxto"));
                    String faxId = checkNull(myRs.getString("faxid"));
                    String archiveId = checkNull(myRs.getString("archiveid"));
                    String action = checkNull(myRs.getString("action"));
                    String notes = checkNull(myRs.getString("notes"));
                    String follow = checkNull(myRs.getString("follow"));
                    String directB = checkNull(myRs.getString("directedbranch"));

                    Object[] followRow = new Object[] { notes, follow, action, directB, faxId, faxDate, to, from,
                            subj, archiveId, date };
                    fTable.getTableModel().addRow(followRow);
                } while (myRs.next());
                fTable.resizeColumnWidth();
                fTable.centerTable();
                JOptionPane.showMessageDialog(null, "يوجد " + myRsLength + " فاكس للمتابعة");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private void onClickYesButton(){
        fYesButton.addActionListener(arg0 -> {
            int selectedRow = fTable.getSelectedRow();
            String[] updateSections = {"followstatus"};
            String[] updateValues = {"0"};
            dbUpdateValues(MAINTABLENAME,"archiveid", (String) fTable.getTableModel().getValueAt(selectedRow, 9),
                    updateSections,updateValues);
            JOptionPane.showMessageDialog(null, "تم إلإنتهاء من متابعة الفاكس المحدد");
            fTable.getTableModel().removeRow(selectedRow);
        });
    }
}
