package pages;

import guiUtils.*;

import javax.swing.*;
import java.awt.*;

import static utils.CommonButtons.*;
import static utils.ConstantAttributes.*;


public class SearchPage extends GridBagPanel {
    // Search Page Components
    // Tables
    private GridBagTable sTable = new GridBagTable(headers, 1, 4);

    // Date Choosers
    private GridBagDateChooser sDate = new GridBagDateChooser(2, 2);

    // Labels
    private GridBagLabel sSearchWordLabel = new GridBagLabel(lSearchWordIcon, SwingConstants.CENTER,
            GridBagConstraints.BOTH, 4, 1);
    private GridBagLabel sSearchWithLabel = new GridBagLabel(lSearchWithIcon, SwingConstants.CENTER,
            GridBagConstraints.BOTH, 6, 1);

    // TextFields
    private GridBagTextField sSearchWordText = new GridBagTextField(2, 1);

    // CheckBoxes
    private GridBagComboBox sSearchWithCombo = new GridBagComboBox(comboBoxItems, 5, 1);

    // Buttons
    private GridBagImageButton sSearchButton = new GridBagImageButton(searchNowIcon, 1, 1);
    private GridBagImageButton sFolderButton = new GridBagImageButton(folderIcon, 1, 6);
    private GridBagImageButton sOpenPDFButton = new GridBagImageButton(pdfIcon, 2, 6);
    private GridBagImageButton sPasteButton = new GridBagImageButton(pasteAction, pasteIcon, 3, 6);
    private GridBagImageButton sCopyButton = new GridBagImageButton(copyAction, copyIcon, 4, 6);
    private GridBagImageButton sPrintButton = new GridBagImageButton(printIcon, 5, 6);
    private GridBagImageButton sBackButton = new GridBagImageButton(homeIcon, 6, 6);

    public SearchPage(int[] colWidth, double[] colWeights, int[] rowHeight, double[] rowWeights) {
        super(colWidth, colWeights, rowHeight, rowWeights);
        loadPage();
    }

    @Override
    public void loadPage() {
        this.add(sTable.getTableScrollPane(), sTable.getGBC());
        this.add(sDate, sDate.getGBC());
        sDate.setVisible(false);
        this.add(sSearchWordLabel, sSearchWordLabel.getGBC());
        this.add(sSearchWithLabel, sSearchWithLabel.getGBC());
        this.add(sSearchWordText, sSearchWordText.getGBC());
        this.add(sSearchWithCombo, sSearchWithCombo.getGBC());
        this.add(sSearchButton, sSearchButton.getGBC());
        this.add(sFolderButton, sFolderButton.getGBC());
        this.add(sOpenPDFButton, sOpenPDFButton.getGBC());
        this.add(sPasteButton, sPasteButton.getGBC());
        this.add(sCopyButton, sCopyButton.getGBC());
        this.add(sPrintButton, sPrintButton.getGBC());
        this.add(sBackButton, sBackButton.getGBC());

        // SearchWith ComboBox Listener Function
        comboBoxListener();

        onClickButtonsListeners();
    }

    public void onClickButtonsListeners() {
        backToHome(sBackButton);
        printPdfFile(sPrintButton, sTable);
        openLink(sOpenPDFButton, sTable);
        openFoldersLink(sFolderButton, sTable);
        findFaxes(sSearchButton,sTable,sSearchWithCombo,sDate,sSearchWordText);
    }

    // SearchWith ComboBox Listener in Search Panel
    private void comboBoxListener() {
        sSearchWithCombo.addActionListener(arg0 -> {
            if (sSearchWithCombo.getSelectedIndex() == 1 || sSearchWithCombo.getSelectedIndex() == 2) {
                sDate.setVisible(true);
                sSearchWordText.setEnabled(false);
            } else {
                sDate.setVisible(false);
                sSearchWordText.setEnabled(true);
            }
        });
    }

}
