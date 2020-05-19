package guiUtils;

import utils.ConstantAttributes;

import javax.swing.*;
import java.awt.*;

public class GridBagTextField extends JTextField {
    private GridBagConstraints gbcTextField = new GridBagConstraints();

    public GridBagTextField(int girdX, int gridY){
        this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        this.setFont(ConstantAttributes.mainFontPlain);
        this.setHorizontalAlignment(SwingConstants.RIGHT);
        this.setColumns(10);
        this.setBorder(new RoundedCornerBorder());
        this.setOpaque(false);
        gbcTextField.gridwidth = 2;
        gbcTextField.insets = new Insets(0, 0, 5, 5);
        gbcTextField.fill = GridBagConstraints.BOTH;
        gbcTextField.gridx = girdX;
        gbcTextField.gridy = gridY;
    }

    public GridBagTextField(int HAlignment, int girdX, int gridY){
        this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        this.setFont(ConstantAttributes.mainFontPlain);
        this.setHorizontalAlignment(HAlignment);
        this.setColumns(10);
        this.setBorder(new RoundedCornerBorder());
        this.setOpaque(false);
        gbcTextField.gridwidth = 2;
        gbcTextField.insets = new Insets(0, 0, 5, 5);
        gbcTextField.fill = GridBagConstraints.BOTH;
        gbcTextField.gridx = girdX;
        gbcTextField.gridy = gridY;
    }

    public GridBagTextField(int gridWidth,int HAlignment, int girdX, int gridY){
        this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        this.setFont(ConstantAttributes.mainFontPlain);
        this.setHorizontalAlignment(HAlignment);
        this.setColumns(10);
        this.setBorder(new RoundedCornerBorder());
        this.setOpaque(false);
        gbcTextField.gridwidth = (int)gridWidth;
        gbcTextField.insets = new Insets(0, 0, 5, 5);
        gbcTextField.fill = GridBagConstraints.BOTH;
        gbcTextField.gridx = girdX;
        gbcTextField.gridy = gridY;
    }
    public GridBagConstraints getGBC() {
        return gbcTextField;
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
