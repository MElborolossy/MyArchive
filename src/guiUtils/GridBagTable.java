package guiUtils;

import utils.ConstantAttributes;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class GridBagTable extends JTable {

    private DefaultTableModel tableModel = new DefaultTableModel();
    private JScrollPane tableScrollPane = new JScrollPane(this);
    private GridBagConstraints gbcTable = new GridBagConstraints();

    public GridBagTable(Object[] headers, int gridX, int gridY){
        tableModel.setColumnIdentifiers(headers);
        this.setRowMargin(1);
        this.setRowHeight(30);
        this.setShowVerticalLines(true);
        this.setShowHorizontalLines(true);
        this.getTableHeader().setFont(ConstantAttributes.mainFontBold);
        this.setFont(ConstantAttributes.mainFontPlain);
        tableScrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        gbcTable.gridwidth = 6;
        gbcTable.insets = new Insets(0, 0, 5, 5);
        gbcTable.fill = GridBagConstraints.BOTH;
        gbcTable.gridx = gridX;
        gbcTable.gridy = gridY;
        centerTable();
        this.setModel(tableModel);
    }

    public GridBagTable(Object[] headers, int gridWidth, int gridX, int gridY){
        this(headers,gridX,gridY);
        gbcTable.gridwidth = gridWidth;
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        if (c instanceof JComponent) {
            JComponent jc = (JComponent) c;
            String toolTipStr = "<html><p><font size =\"5\">" + getValueAt(row, column).toString()
                    + "</font></p></html>";
            jc.setToolTipText(toolTipStr);
        }
        return c;
    }

    public GridBagConstraints getGBC() {
        return gbcTable;
    }
    public DefaultTableModel getTableModel() {
        return tableModel;
    }
    public JScrollPane getTableScrollPane(){
        return tableScrollPane;
    }
    public void centerTable() {
        final TableCellRenderer cellRender = this.getTableHeader().getDefaultRenderer();
        // Center Table Headers
        this.getTableHeader().setDefaultRenderer((table, value, isSelected, hasFocus, row, column) -> {
            JLabel lbl = (JLabel) cellRender.getTableCellRendererComponent(table,
                    value, isSelected, hasFocus, row, column);
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            lbl.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));;
            lbl.setOpaque(false);
            return lbl;
        });
        // Right alignment for table cells
        DefaultTableCellRenderer rightAlignment = new DefaultTableCellRenderer();
        rightAlignment.setHorizontalAlignment(SwingConstants.RIGHT);

        int colCount = this.getModel().getColumnCount();
        for (int i = 0; i < colCount; i++) {
            this.getColumnModel().getColumn(i).setCellRenderer(rightAlignment);
        }
    }
    public  void resizeColumnWidth() {
        final TableColumnModel columnModel = this.getColumnModel();
        for (int column = 0; column < this.getColumnCount(); column++) {
            int width = 10;
            for (int row = 0; row < this.getRowCount(); row++) {
                TableCellRenderer renderer = this.getCellRenderer(row, column);
                Component comp = this.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            if (width > 300)
                width = 300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }
}
