package guiUtils;

import utils.ConstantAttributes;

import javax.swing.border.TitledBorder;
import java.awt.*;

public class GridBagTitledPanel extends GridBagPanel {
    private GridBagConstraints panelGBC = new GridBagConstraints();

    public GridBagTitledPanel(String title, int gridX, int gridY,int[] colWidth, double[] colWeights, int[] rowHeight,
                              double[] rowWeights){
        super(new StretchIcon(new byte[]{0}),colWidth,colWeights,rowHeight,rowWeights);
        this.setBorder(new TitledBorder(new RoundedCornerBorder(),title,TitledBorder.RIGHT,TitledBorder.TOP, ConstantAttributes.mainFontBold,Color.WHITE));
        this.setOpaque(false);
        panelGBC.insets = new Insets(0, 0, 5, 5);
        panelGBC.fill = GridBagConstraints.BOTH;
        panelGBC.gridx = gridX;
        panelGBC.gridy = gridY;

        this.setLayout(super.getLayout());
    }

    public GridBagConstraints getGBC() {
        return panelGBC;
    }
}
