package guiUtils;

import utils.ConstantAttributes;

import javax.swing.*;
import java.awt.*;

public class GridBagComboBox extends JComboBox {
    private GridBagConstraints gbcComboBox = new GridBagConstraints();
    private DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();

    public GridBagComboBox(String [] comboBoxItems, int gridX, int gridY){
        for(String item: comboBoxItems){
            comboBoxModel.addElement(item);
        }
        this.setFont(ConstantAttributes.mainFontBold);
        this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        ((JLabel) this.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);
        this.setModel(comboBoxModel);

        gbcComboBox.insets = new Insets(0, 0, 5, 5);
        gbcComboBox.fill = GridBagConstraints.BOTH;
        gbcComboBox.gridx = gridX;
        gbcComboBox.gridy = gridY;
    }

    public GridBagConstraints getGBC() {
        return gbcComboBox;
    }
}