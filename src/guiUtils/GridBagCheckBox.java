package guiUtils;

import javax.swing.*;
import java.awt.*;

public class GridBagCheckBox extends JCheckBox {
    private GridBagConstraints gbcCheckBox = new GridBagConstraints();
    
    public GridBagCheckBox(ImageIcon icon, ImageIcon selectedIcon,int gridX, int gridY){
		this.setIcon(icon);
		this.setSelectedIcon(selectedIcon);
        this.setBorder(null);
        this.setOpaque(false);
        this.setHorizontalAlignment(SwingConstants.RIGHT);
        this.setHorizontalTextPosition(SwingConstants.LEFT);

        gbcCheckBox.anchor = GridBagConstraints.EAST;
        gbcCheckBox.fill = GridBagConstraints.VERTICAL;
        gbcCheckBox.insets = new Insets(0, 0, 5, 5);
        gbcCheckBox.gridx = gridX;
        gbcCheckBox.gridy = gridY;
    }
    public GridBagConstraints getGBC() {
        return gbcCheckBox;
    }
}
