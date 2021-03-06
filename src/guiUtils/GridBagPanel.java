package guiUtils;

import pages.MainPage;

import javax.swing.*;
import java.awt.*;

import static utils.ConstantAttributes.*;

public class GridBagPanel extends JPanel {
    private StretchIcon img;
    private GridBagLayout panelGBL = new GridBagLayout();

    public GridBagPanel(StretchIcon img, int[] colWidth, double[] colWeights, int[] rowHeight,
                        double[] rowWeights) {
        this.setOpaque(false);
        this.img = img;
        this.setBackground(backDarkColor);
        this.setFont(mainFontBold);
        panelGBL.columnWidths = colWidth;
        panelGBL.columnWeights = colWeights;
        panelGBL.rowHeights = rowHeight;
        panelGBL.rowWeights = rowWeights;
        this.setLayout(panelGBL);
    }

    public GridBagPanel(int[] colWidth, double[] colWeights, int[] rowHeight,
                        double[] rowWeights) {
        this.setOpaque(false);
        this.img = pagesDarkBackgroundImage;
        this.setBackground(backDarkColor);
        this.setFont(mainFontBold);
        panelGBL.columnWidths = colWidth;
        panelGBL.columnWeights = colWeights;
        panelGBL.rowHeights = rowHeight;
        panelGBL.rowWeights = rowWeights;
        this.setLayout(panelGBL);
    }

    public GridBagPanel(GridBagTitledPanel panel, int[] colWidth, double[] colWeights, int[] rowHeight,
                        double[] rowWeights) {
        this(colWidth, colWeights, rowHeight, rowWeights);
        this.add(panel, panel.getGBC());
    }

    @Override
    public void paintComponent(Graphics g) {
        img.paintIcon(this, g, 0, 0);
    }

    public GridBagLayout getGBL() {
        return panelGBL;
    }

    public void loadPage() {
    }
    
    // *** Change Centered Panel to be shown ***\\
    public void centerPanel(JLayeredPane layer) {
        layer.removeAll();
        layer.add(this, BorderLayout.CENTER);
        layer.validate();
    }

    public void disablePage(){
        for (Component c : this.getComponents()) {
            if (c instanceof GridBagImageButton ){
                if( ((GridBagImageButton) c).getIcon().toString().contains("Home") || ((GridBagImageButton) c).getIcon().toString().contains("Back") ){
                    c.setEnabled(true);
                }else c.setEnabled(false);
            }
            else c.setEnabled(false);
        }
    }

    public void reloadPage(){
        for (Component c : this.getComponents()) {
            c.setEnabled(true);
        }
    }

    public void lightMode(){
        if(this instanceof MainPage){
            this.img = mainLightBackgroundImage;
        }else{
            this.img = pagesLightBackgroundImage;
        }
        this.setBackground(backLightColor);
        this.validate();
    }

    public void darkMode(){
        if(this instanceof MainPage){
            this.img = mainDarkBackgroundImage;
        }else{
            this.img = pagesDarkBackgroundImage;
        }
        this.setBackground(backDarkColor);
        this.validate();
    }

}
