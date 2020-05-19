package guiUtils;

import utils.ConstantAttributes;

import javax.swing.*;
import java.awt.*;

public class GridBagLabel extends JLabel {
	private GridBagConstraints gbcLabel = new GridBagConstraints();

	// Constructor with ImageIcon, fill, and other common Options
	public GridBagLabel(ImageIcon icon, int HTextPosition, int fill, int gridX, int gridY) {
		this.setFont(ConstantAttributes.mainFontBold);
		this.setIcon(icon);
		this.setHorizontalTextPosition(HTextPosition);
		gbcLabel.fill = fill;
		gbcLabel.insets = new Insets(0, 0, 5, 5);
		gbcLabel.gridx = gridX;
		gbcLabel.gridy = gridY;
	}
	
	// Constructor with Text, fill, and other common Options
		public GridBagLabel(String text, int HTextPosition, int fill, int gridX, int gridY) {
			this.setFont(ConstantAttributes.mainFontPlain);
			this.setText(text);
			this.setHorizontalTextPosition(HTextPosition);
			this.setBorder(new RoundedCornerBorder());
			this.setOpaque(false);
			gbcLabel.fill = fill;
			gbcLabel.insets = new Insets(0, 0, 5, 5);
			gbcLabel.gridx = gridX;
			gbcLabel.gridy = gridY;
		}

	public GridBagLabel(ImageIcon icon,int gridWidth, int anchor, int fill, int gridX, int gridY) {
		this(icon,SwingConstants.CENTER,fill,gridX,gridY);
		gbcLabel.gridwidth = gridWidth;
		gbcLabel.anchor = anchor;
	}

	public GridBagLabel(ImageIcon icon,long anchor, int fill, int gridX, int gridY) {
		this(icon,SwingConstants.CENTER,fill,gridX,gridY);
		gbcLabel.anchor = (int)anchor;
	}

	
	// Get GridBagConstrains
	public GridBagConstraints getGBC() {
		return gbcLabel;
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setPaint(getBackground());
			g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(
					0, 0, getWidth() - 1, getHeight() - 1));
			g2.dispose();
		}
		super.paintComponent(g);
	}
}
