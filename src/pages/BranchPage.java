package pages;

import guiUtils.GridBagImageButton;
import guiUtils.GridBagLabel;
import guiUtils.GridBagPanel;
import guiUtils.GridBagTable;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static main.MyArchive.*;
import static utils.CommonButtons.copyAction;
import static utils.Connections.isConnected;
import static utils.Connections.myConn;
import static utils.ConstantAttributes.*;
import static utils.MYSQL.*;

public class BranchPage extends GridBagPanel {
	// Branches Page Components
	// Tables
	private GridBagTable bExtTable = new GridBagTable(branchHeaders, 5, 1, 3);
	private GridBagTable bIntTable = new GridBagTable(branchHeaders, 5, 7, 3);

	// Labels
	private GridBagLabel bExtLabel = new GridBagLabel(lEBranchIcon, 5,GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			1, 1);
	private GridBagLabel bIntLabel = new GridBagLabel(lIBranchIcon, 5, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			7, 1);

	// Buttons
	private GridBagImageButton bDelExtButton = new GridBagImageButton(delEBranchIcon, 1, 5);
	private GridBagImageButton bDelIntButton = new GridBagImageButton(delIBranchIcon, 7, 5);
	private GridBagImageButton bEditExtButton = new GridBagImageButton(editEBranchIcon, 3, 5);
	private GridBagImageButton bEditIntButton = new GridBagImageButton(editIBranchIcon, 9, 5);
	private GridBagImageButton bAddExtButton = new GridBagImageButton(addEBranchIcon, 5, 5);
	private GridBagImageButton bAddIntButton = new GridBagImageButton(addIBranchIcon, 11, 5);
	private GridBagImageButton bBackButton = new GridBagImageButton(backIcon, 7, 7);
	private GridBagImageButton bCopyButton = new GridBagImageButton(copyAction,copyIcon, 5, 7);

	public BranchPage(int[] colWidth, double[] colWeights, int[] rowHeight, double[] rowWeights) {
		super(colWidth, colWeights, rowHeight, rowWeights);
		loadPage();

	}

	@Override
	public void loadPage() {
		this.add(bExtTable.getTableScrollPane(), bExtTable.getGBC());
		this.add(bIntTable.getTableScrollPane(), bIntTable.getGBC());
		this.add(bExtLabel, bExtLabel.getGBC());
		this.add(bIntLabel, bIntLabel.getGBC());
		this.add(bDelExtButton, bDelExtButton.getGBC());
		this.add(bDelIntButton, bDelIntButton.getGBC());
		this.add(bEditExtButton, bEditExtButton.getGBC());
		this.add(bEditIntButton, bEditIntButton.getGBC());
		this.add(bAddExtButton, bAddExtButton.getGBC());
		this.add(bAddIntButton, bAddIntButton.getGBC());
		this.add(bBackButton, bBackButton.getGBC());
		this.add(bCopyButton, bCopyButton.getGBC());
		onClickButtonsListeners();
		if(isConnected){
			loadTable(bIntTable, INTERNALTABLE);
			loadTable(bExtTable, EXTERNALTABLE);
		}
	}

	public void onClickButtonsListeners() {
		backToAddPage(bBackButton);
		onClickDelIButton();
		onClickDelEButton();
		onClickEditIButton();
		onClickEditEButton();
		onClickAddIButton();
		onClickAddEButton();

	}

	public static void backToAddPage(GridBagImageButton button) {
		button.addActionListener(arg0 -> {
			addPage.centerPanel(layeredPane);
			if(isConnected){
				addPage.loadComboBoxLists();
				addPage.setHScrollBarPage();
				editPage.loadComboBoxLists();
				editPage.setHScrollBarPage();
			}
		});
	}

	private void onClickDelIButton(){
		bDelIntButton.addActionListener(arg0 -> deleteFromTable(bIntTable,INTERNALTABLE));
	}
	private void onClickDelEButton(){
		bDelExtButton.addActionListener(arg0 -> deleteFromTable(bExtTable,EXTERNALTABLE));
	}
	private void onClickEditIButton(){
		bEditIntButton.addActionListener(arg0 -> editInTable(bIntTable, INTERNALTABLE));
	}
	private void onClickEditEButton(){
		bEditExtButton.addActionListener(arg0 -> editInTable(bExtTable,EXTERNALTABLE));
	}
	private void onClickAddIButton(){
		bAddIntButton.addActionListener(arg0 -> addInTable(bIntTable,INTERNALTABLE));
	}
	private void onClickAddEButton(){
		bAddExtButton.addActionListener(arg0 -> addInTable(bExtTable,EXTERNALTABLE));
	}

	private void loadTable(GridBagTable table, String tableName){
		try {
			table.getTableModel().setRowCount(0);
			ResultSet myRs = dbGetTableResult(tableName);
			int id = 0;
			while (myRs.next()) {
				id = myRs.getInt("id");
				String name = myRs.getString("name");
				Object[] rowB = new Object[]{name, id};
				table.getTableModel().addRow(rowB);
			}
			table.getTableModel().addRow(new Object[]{"", ""});

			table.resizeColumnWidth();
			table.centerTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void deleteFromTable(GridBagTable table, String tableName){
		int selectedRow = table.getSelectedRow();
		String id = table.getTableModel().getValueAt(selectedRow, 1).toString();
		try {
			Statement myStat = myConn.createStatement();
			dbDeleteValueAI(tableName, "id", id);
			loadTable(table,tableName);
			int tableLength = table.getRowCount();
			String sql = "Alter TABLE " + tableName + " AUTO_INCREMENT = " + tableLength;
			myStat.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void editInTable(GridBagTable table, String tableName){
		int selectedRow = table.getSelectedRow();
		String name = table.getTableModel().getValueAt(selectedRow, 0).toString();
		String id = table.getTableModel().getValueAt(selectedRow, 1).toString();
		dbUpdateValues(tableName, "id", id, new String[]{"name"}, new String[]{name});
	}
	private void addInTable(GridBagTable table, String tableName){
		int selectedRow = table.getSelectedRow();
		String name = (String) table.getTableModel().getValueAt(selectedRow, 0);
		dbInsertValueAI(tableName, "name",name);
		loadTable(table,tableName);
	}

}
