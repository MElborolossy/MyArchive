package guiUtils;

import com.jidesoft.swing.CheckBoxList;
import com.jidesoft.swing.SearchableUtils;
import utils.ConstantAttributes;

import javax.swing.*;
import java.awt.*;

public class GridBagCheckBoxList extends CheckBoxList {
    private GridBagConstraints gbcCheckBoxList = new GridBagConstraints();
    private DefaultListModel<String> checkBoxListModel = new DefaultListModel<>();
    private JScrollPane checkBoxListScrollPane = new JScrollPane();

    public GridBagCheckBoxList(int gridX, int gridY){
        this.setFont(ConstantAttributes.mainFontPlain);
        this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        SearchableUtils.installSearchable(this).setFromStart(false);
        this.setModel(checkBoxListModel);
        this.setVisibleRowCount(1);
        checkBoxListScrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        checkBoxListScrollPane.getHorizontalScrollBar().setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        checkBoxListScrollPane.setViewportView(this);

        gbcCheckBoxList.gridwidth = 2;
        gbcCheckBoxList.insets = new Insets(0, 0, 5, 5);
        gbcCheckBoxList.fill = GridBagConstraints.BOTH;
        gbcCheckBoxList.gridx = gridX;
        gbcCheckBoxList.gridy = gridY;

    }

    public GridBagConstraints getGBC() {
        return gbcCheckBoxList;
    }
    public DefaultListModel<String> getCheckBoxListModel() {
        return checkBoxListModel;
    }
    public JScrollPane getScrollPane(){
        return checkBoxListScrollPane;
    }

    // *** Get CheckList Elements Value ***\\
    public String getCheckedValues() {
        String checkedStr = "";
        int[] checkedIndex = this.getCheckBoxListSelectedIndices();
        for (int i = 0; i < checkedIndex.length; i++) {
            if (i == checkedIndex.length - 1) {
                checkedStr += this.getModel().getElementAt(checkedIndex[i]);
            } else {
                checkedStr += this.getModel().getElementAt(checkedIndex[i]) + " ,";
            }
        }
        return checkedStr;
    }
}
