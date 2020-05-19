package pages;

import guiUtils.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static main.MyArchive.layeredPane;
import static utils.CommonButtons.*;
import static utils.CommonMethods.loadBranches;
import static utils.CommonMethods.setHScrollBar;
import static utils.Connections.isConnected;
import static utils.ConstantAttributes.*;
import static utils.MYSQL.dbDeleteValue;
import static utils.MYSQL.dbUpdateValues;

public class EditPage extends GridBagPanel {
	// Edit page components
	private boolean isLoaded = false;
	// Table
	private final GridBagTable eTable = new GridBagTable(headers, 1, 3);

	// Date Choosers
	private final GridBagDateChooser eOpenWithDate = new GridBagDateChooser(4, 2);
	private final GridBagDateChooser eDate = new GridBagDateChooser(1, 4);
	private final GridBagDateChooser eFaxDate = new GridBagDateChooser(1, 6);

	// Labels
	private final GridBagLabel eOpenWithLabel = new GridBagLabel(lOpenWithIcon, SwingConstants.CENTER, GridBagConstraints.VERTICAL, 6, 1);

	// TextFields
	private final GridBagTextField eOpenWithText = new GridBagTextField(1, SwingConstants.RIGHT, 4, 1);
	private final GridBagTextField eSubjText = new GridBagTextField(4, 4);
	private final GridBagTextField eFaxText = new GridBagTextField(4, 6);
	private final GridBagTextField eNoteText = new GridBagTextField(1, 12);
	private final GridBagTextField eActionText = new GridBagTextField(4, 12);
	private final GridBagTextField eFileText = new GridBagTextField(4, 14);

	// CheckBoxLists
	private final GridBagCheckBoxList eDirectList = new GridBagCheckBoxList(1, 10);
	private final GridBagCheckBoxList eToList = new GridBagCheckBoxList(1, 8);
	private final GridBagCheckBoxList eFromList = new GridBagCheckBoxList(4, 8);
	private final GridBagCheckBoxList eFollowList = new GridBagCheckBoxList(4, 10);

	// CheckBoxes
	private final GridBagCheckBox eDateCheck = new GridBagCheckBox(cDateIcon, cSDateIcon, 3, 4);
	private final GridBagCheckBox eSubjCheck = new GridBagCheckBox(cSubjIcon, cSSubjIcon, 6, 4);
	private final GridBagCheckBox eFaxDateCheck = new GridBagCheckBox(cDateFaxIcon, cSDateFaxIcon, 3, 6);
	private final GridBagCheckBox eFaxCheck = new GridBagCheckBox(cFaxIcon, cSFaxIcon, 6, 6);
	private final GridBagCheckBox eToCheck = new GridBagCheckBox(cToIcon, cSToIcon, 3, 8);
	private final GridBagCheckBox eFromCheck = new GridBagCheckBox(cFromIcon, cSFromIcon, 6, 8);
	private final GridBagCheckBox eDirectCheck = new GridBagCheckBox(cDirectedIcon, cSDirectedIcon, 3, 10);
	private final GridBagCheckBox eFollowCheck = new GridBagCheckBox(cFollowIcon, cSFollowIcon, 6, 10);
	private final GridBagCheckBox eNoteCheck = new GridBagCheckBox(cNoteIcon, cSNoteIcon, 3, 12);
	private final GridBagCheckBox eActionCheck = new GridBagCheckBox(cActionIcon, cSActionIcon, 6, 12);
	private final GridBagCheckBox eFileCheck = new GridBagCheckBox(cFileIcon, cSFileIcon, 6, 14);

	// ComboBoxes
	private final GridBagComboBox eOpenWithCombo = new GridBagComboBox(comboBoxItems, 5, 1);

	// Buttons
	private final GridBagImageButton eOpenButton = new GridBagImageButton(openIcon, 3, 1);
	private final GridBagImageButton eEditButton = new GridBagImageButton(saveIcon, 1, 15);
	private final GridBagImageButton eCopyButton = new GridBagImageButton(copyAction, copyIcon, 3, 15);
	private final GridBagImageButton eOpenPDFButton = new GridBagImageButton(pdfIcon, 4, 15);
	private final GridBagImageButton eBackButton = new GridBagImageButton(homeIcon, 6, 15);
	private final GridBagImageButton eDelButton = new GridBagImageButton(deleteIcon, 1, 16);
	private final GridBagImageButton ePasteButton = new GridBagImageButton(pasteAction, pasteIcon, 3, 16);
	private final GridBagImageButton eFolderButton = new GridBagImageButton(folderIcon, 4, 16);
	private final GridBagImageButton eFileButton = new GridBagImageButton(addLinkIcon, 6, 16);

	public EditPage(int[] colWidth, double[] colWeights, int[] rowHeight, double[] rowWeights) {
		super(colWidth, colWeights, rowHeight, rowWeights);
		loadPage();
	}

	@Override
	public void loadPage() {
		this.add(eTable.getTableScrollPane(), eTable.getGBC());
		this.add(eOpenWithCombo, eOpenWithCombo.getGBC());
		this.add(eOpenWithDate, eOpenWithDate.getGBC());
		eOpenWithDate.setVisible(false);
		this.add(eDate, eDate.getGBC());
		this.add(eFaxDate, eFaxDate.getGBC());
		this.add(eOpenWithLabel, eOpenWithLabel.getGBC());
		this.add(eOpenWithText, eOpenWithText.getGBC());
		this.add(eSubjText, eSubjText.getGBC());
		this.add(eFaxText, eFaxText.getGBC());
		this.add(eNoteText, eNoteText.getGBC());
		this.add(eActionText, eActionText.getGBC());
		this.add(eFileText, eFileText.getGBC());
		this.add(eDirectList.getScrollPane(), eDirectList.getGBC());
		this.add(eToList.getScrollPane(), eToList.getGBC());
		this.add(eFromList.getScrollPane(), eFromList.getGBC());
		this.add(eFollowList.getScrollPane(), eFollowList.getGBC());
		this.add(eDateCheck, eDateCheck.getGBC());
		this.add(eSubjCheck, eSubjCheck.getGBC());
		this.add(eFaxDateCheck, eFaxDateCheck.getGBC());
		this.add(eFaxCheck, eFaxCheck.getGBC());
		this.add(eToCheck, eToCheck.getGBC());
		this.add(eFromCheck, eFromCheck.getGBC());
		this.add(eDirectCheck, eDirectCheck.getGBC());
		this.add(eFollowCheck, eFollowCheck.getGBC());
		this.add(eNoteCheck, eNoteCheck.getGBC());
		this.add(eActionCheck, eActionCheck.getGBC());
		this.add(eFileCheck, eFileCheck.getGBC());
		this.add(eOpenButton, eOpenButton.getGBC());
		this.add(eEditButton, eEditButton.getGBC());
		this.add(eCopyButton, eCopyButton.getGBC());
		this.add(eOpenPDFButton, eOpenPDFButton.getGBC());
		this.add(eBackButton, eBackButton.getGBC());
		this.add(eDelButton, eDelButton.getGBC());
		this.add(ePasteButton, ePasteButton.getGBC());
		this.add(eFolderButton, eFolderButton.getGBC());
		this.add(eFileButton, eFileButton.getGBC());

		// OpenWith ComboBox Listener Function
		comboBoxListener();
		if(isConnected){
			loadComboBoxLists();
		}
		onClickButtonsListeners();

	}

	public void loadComboBoxLists() {
		loadBranches(eDirectList.getCheckBoxListModel(), INTERNALTABLE);
		loadBranches(eToList.getCheckBoxListModel(), EXTERNALTABLE);
		loadBranches(eFromList.getCheckBoxListModel(), EXTERNALTABLE);
		loadBranches(eFollowList.getCheckBoxListModel(), INTERNALTABLE);
	}

	@Override
	public void centerPanel(JLayeredPane layer) {
		super.centerPanel(layeredPane);
		if (!isLoaded) {
			setHScrollBarPage();
			isLoaded = true;
		}

	}

	public void setHScrollBarPage() {
		setHScrollBar(eDirectList.getScrollPane());
		setHScrollBar(eToList.getScrollPane());
		setHScrollBar(eFromList.getScrollPane());
		setHScrollBar(eFollowList.getScrollPane());
	}

	private void onClickButtonsListeners() {
		backToHome(eBackButton);
		openLink(eOpenPDFButton, eTable);
		openFoldersLink(eFolderButton, eTable);
		chooseLink(eFileButton,eFileText);
		findFaxes(eOpenButton,eTable,eOpenWithCombo,eOpenWithDate,eOpenWithText);
		onClickEditButton();
		onClickDeleteButton();
	}

	// OpenWith ComboBox Listener in Edit Panel
	private void comboBoxListener() {
		eOpenWithCombo.addActionListener(arg0 -> {
			if (eOpenWithCombo.getSelectedIndex() == 1 || eOpenWithCombo.getSelectedIndex() == 2) {
				eOpenWithDate.setVisible(true);
				eOpenWithText.setEnabled(false);
			} else {
				eOpenWithDate.setVisible(false);
				eOpenWithText.setEnabled(true);
			}
		});
	}

	private void onClickEditButton(){
		eEditButton.addActionListener(arg0 -> {
			int selectedRow = eTable.getSelectedRow();
			if(selectedRow >= 0) {
				boolean anyUpdate = false;
				String followStr;
				String followStatus;
				String idStr =  eTable.getTableModel().getValueAt(selectedRow, 9).toString();

				ArrayList<String> updateSections = new ArrayList<>();
				ArrayList<String> updateValues = new ArrayList<>();
				String[] updateSectionsStr;
				String[] updateValuesStr;

				if (eDateCheck.isSelected()) {
					updateSections.add("date");
					updateValues.add(df.format(eDate.getDate()));
					anyUpdate = true;
					eTable.getTableModel().setValueAt(df.format(eDate.getDate()), selectedRow, 10);
				}
				if (eSubjCheck.isSelected()) {
					updateSections.add("subject");
					updateValues.add(eSubjText.getText());
					anyUpdate = true;
					eTable.getTableModel().setValueAt(eSubjText.getText(), selectedRow, 8);
				}
				if (eFromCheck.isSelected()) {
					updateSections.add("faxfrom");
					updateValues.add(eFromList.getCheckedValues());
					anyUpdate = true;
					eTable.getTableModel().setValueAt(eFromList.getCheckedValues(), selectedRow, 7);
				}
				if (eToCheck.isSelected()) {
					updateSections.add("faxto");
					updateValues.add(eToList.getCheckedValues());
					anyUpdate = true;
					eTable.getTableModel().setValueAt(eToList.getCheckedValues(), selectedRow, 6);
				}
				if (eFaxDateCheck.isSelected()) {
					updateSections.add("faxdate");
					updateValues.add(df.format(eFaxDate.getDate()));
					anyUpdate = true;

					eTable.getTableModel().setValueAt(df.format(eFaxDate.getDate()), selectedRow, 5);
				}
				if (eFaxCheck.isSelected()) {
					updateSections.add("faxid");
					updateValues.add(eFaxText.getText());
					anyUpdate = true;
					eTable.getTableModel().setValueAt(eFaxText.getText(), selectedRow, 4);
				}
				if (eDirectCheck.isSelected()) {
					updateSections.add("directedbranch");
					updateValues.add(eDirectList.getCheckedValues());
					anyUpdate = true;
					eTable.getTableModel().setValueAt(eDirectList.getCheckedValues(), selectedRow, 3);
				}
				if (eActionCheck.isSelected()) {
					updateSections.add("action");
					updateValues.add(eActionText.getText());
					anyUpdate = true;
					eTable.getTableModel().setValueAt(eActionText.getText(), selectedRow, 2);
				}
				if (eFollowCheck.isSelected()) {
					followStr = eFollowList.getCheckedValues();
					updateSections.add("follow");
					updateValues.add(followStr);
					eTable.getTableModel().setValueAt(followStr, selectedRow, 1);

					if (followStr.equals("لا يوجد") || followStr.equals("")) followStatus = "0";
					else followStatus = "1";
					updateSections.add("followstatus");
					updateValues.add(followStatus);
					anyUpdate = true;
				}
				if (eNoteCheck.isSelected()) {
					updateSections.add("notes");
					updateValues.add(eNoteText.getText());
					anyUpdate = true;
					eTable.getTableModel().setValueAt(eNoteText.getText(), selectedRow, 0);
				}
				if (eFileCheck.isSelected()) {
					updateSections.add("link");
					updateValues.add(eFileText.getText());
					anyUpdate = true;
				}
				if (anyUpdate) {
					updateSectionsStr = Arrays.copyOf(updateSections.toArray(), updateSections.toArray().length, String[].class);
					updateValuesStr = Arrays.copyOf(updateValues.toArray(), updateValues.toArray().length, String[].class);
					dbUpdateValues(MAINTABLENAME, "archiveid", idStr, updateSectionsStr, updateValuesStr);
					JOptionPane.showMessageDialog(null, "تم التعديل بنجاح");
					resetAllFields();
				} else {
					JOptionPane.showMessageDialog(null, "الرجاء اختيار أحد الخيارات لتتمكن من التعديل");
				}

			}else{
				JOptionPane.showMessageDialog(null, "الرجاء البحث عن فاكس أولًا");
			}
		});
	}
	private void onClickDeleteButton(){
		eDelButton.addActionListener(arg0 -> {
			int selectedRow = eTable.getSelectedRow();
			String id = eTable.getTableModel().getValueAt(selectedRow, 9).toString();
			int isSure = JOptionPane.showConfirmDialog(null, "هل تريد مسح الفاكس المحدد ؟", "تأكيد المسح", JOptionPane.YES_NO_OPTION);
			if(isSure == JOptionPane.YES_OPTION){
				dbDeleteValue(MAINTABLENAME, "archiveid", id);
				eTable.getTableModel().removeRow(selectedRow);
				JOptionPane.showMessageDialog(null, "تم مسح الفاكس المحدد من قاعدة البيانات");

			}else{
				JOptionPane.showMessageDialog(null, "لم يتم مسح الفاكس المحدد");

			}
		});
	}

	private void resetAllFields(){
		int[] ind = {-1};
		eFromList.setCheckBoxListSelectedIndices(ind);
		eToList.setCheckBoxListSelectedIndices(ind);
		eFollowList.setCheckBoxListSelectedIndices(ind);
		eDirectList.setCheckBoxListSelectedIndices(ind);

		eSubjText.setText("");
		eNoteText.setText("");
		eFaxText.setText("");
		eActionText.setText("");
		eFileText.setText("");

		eDate.cleanup();
		eFaxDate.cleanup();
		eOpenWithDate.cleanup();

		eSubjCheck.setSelected(false);
		eDateCheck.setSelected(false);
		eFaxCheck.setSelected(false);
		eFaxDateCheck.setSelected(false);
		eFromCheck.setSelected(false);
		eToCheck.setSelected(false);
		eFollowCheck.setSelected(false);
		eDirectCheck.setSelected(false);
		eActionCheck.setSelected(false);
		eNoteCheck.setSelected(false);
		eFileCheck.setSelected(false);
	}

}
