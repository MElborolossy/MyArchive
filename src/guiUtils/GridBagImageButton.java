package guiUtils;

import javax.swing.*;
import java.awt.*;

public class GridBagImageButton extends JButton{
	
	private GridBagConstraints gbcButton = new GridBagConstraints();
	
	public GridBagImageButton(ImageIcon icon, int anchor, int gridX, int gridY) {
		this.setIcon(icon);
		this.setBorder(null);
		this.setContentAreaFilled(false);
		gbcButton.anchor = anchor;
		gbcButton.fill = GridBagConstraints.VERTICAL;
		gbcButton.insets = new Insets(0, 0, 5, 5);
		gbcButton.gridx = gridX;
		gbcButton.gridy = gridY;
	}
	
	public GridBagImageButton(ImageIcon icon, int gridX, int gridY) {
		this.setIcon(icon);
		this.setBorder(null);
		this.setContentAreaFilled(false);
		gbcButton.fill = GridBagConstraints.VERTICAL;
		gbcButton.insets = new Insets(0, 0, 5, 5);
		gbcButton.gridx = gridX;
		gbcButton.gridy = gridY;
	}

	public GridBagImageButton(Action action, ImageIcon icon, int gridX, int gridY) {
		setAction(action);
		this.setIcon(icon);
		this.setBorder(null);
		this.setContentAreaFilled(false);
		gbcButton.fill = GridBagConstraints.VERTICAL;
		gbcButton.insets = new Insets(0, 0, 5, 5);
		gbcButton.gridx = gridX;
		gbcButton.gridy = gridY;
	}
	
	public GridBagConstraints getGBC() {
		return gbcButton;
	}
	

}
