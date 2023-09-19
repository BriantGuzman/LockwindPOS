package pay.point.sample;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class InvoiceDashboard {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Spaceship Cockpit Dashboard");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(0, 0, 0)); // Black background

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        String[] columnNames = {"Invoice ID", "Invoice Date", "Invoice Time"};
        Object[][] data = {
                {"INV001", "2023-06-15", "10:30 AM"},
                {"INV002", "2023-06-16", "11:15 AM"},
                {"INV003", "2023-06-17", "12:00 PM"}
        };

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);

        JTable table = new JTable(tableModel);
        table.setBackground(new Color(0, 0, 0));
        table.setForeground(new Color(255, 255, 255)); // White text color
        table.setGridColor(new Color(255, 255, 255)); // White grid color
        table.setSelectionBackground(new Color(50, 50, 50)); // Dark gray selection background

        JTableHeader header = table.getTableHeader();
        header.setFont(getSpaceshipFont(18));
        header.setForeground(new Color(255, 255, 255)); // White text color

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (column == 0 && row == 0) {
                    setIcon(getSpaceshipIcon("/icons/spaceship.png"));
                } else if (column == 0 && row == 1) {
                    setIcon(getSpaceshipIcon("/icons/rocket.png"));
                } else if (column == 0 && row == 2) {
                    setIcon(getSpaceshipIcon("/icons/satellite.png"));
                } else {
                    setIcon(null);
                }

                return component;
            }
        };

        table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(0, 0, 0));
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.getContentPane().add(panel);

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static Font getSpaceshipFont(int size) {
        InputStream inputStream = InvoiceDashboard.class.getResourceAsStream("/fonts/SpaceshipFont.ttf");
        try {
            Font spaceshipFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            return spaceshipFont.deriveFont(Font.PLAIN, size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, size);
        }
    }

    private static ImageIcon getSpaceshipIcon(String path) {
        URL imageUrl = InvoiceDashboard.class.getResource(path);
        if (imageUrl != null) {
            return new ImageIcon(imageUrl);
        } else {
            return null;
        }
    }
}

