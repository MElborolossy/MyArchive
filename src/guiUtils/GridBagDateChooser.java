package guiUtils;

import com.toedter.calendar.JDateChooser;
import utils.ConstantAttributes;

import javax.swing.*;
import java.awt.*;

public class GridBagDateChooser extends JDateChooser {

    private GridBagConstraints gbcDate = new GridBagConstraints();

    public GridBagDateChooser(int gridX, int gridY){
        this.getCalendarButton().setHorizontalTextPosition(SwingConstants.RIGHT);
        this.getCalendarButton().setFont(ConstantAttributes.mainFontPlain);
        this.setFont(ConstantAttributes.dateFontPlain);
        this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        this.setDateFormatString("yyyy-MM-dd");
        this.setBorder(new RoundedCornerBorder());
        this.setOpaque(false);
        gbcDate.gridwidth = 2;
        gbcDate.insets = new Insets(0, 0, 5, 5);
        gbcDate.fill = GridBagConstraints.BOTH;
        gbcDate.gridx = gridX;
        gbcDate.gridy = gridY;
    }

    public GridBagConstraints getGBC() {
        return gbcDate;
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
