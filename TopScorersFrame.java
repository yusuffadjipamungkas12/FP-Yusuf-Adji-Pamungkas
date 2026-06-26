import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class TopScorersFrame extends JFrame {

    private PlayerService playerService;

    public TopScorersFrame() {
        playerService = new PlayerService();
        setupUI();
    }

    private void setupUI() {
        setTitle("Top 5 Scorers");
        setSize(480, 340);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(30, 30, 46));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Judul
        JLabel lblTitle = new JLabel("🏆  Top 5 Scorers", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(new Color(250, 219, 96));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // Tabel
        String[]          kolom = {"#", "Username", "Menang", "Kalah", "Seri", "Skor"};
        DefaultTableModel model = new DefaultTableModel(kolom, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        ArrayList<Player> topPlayers = playerService.getTopFiveScorers();
        for (int i = 0; i < topPlayers.size(); i++) {
            Player p = topPlayers.get(i);
            model.addRow(new Object[]{
                i + 1,
                p.getUsername(),
                p.getWins(),
                p.getLosses(),
                p.getDraws(),
                p.getScore()
            });
        }

        JTable table = new JTable(model);
        table.setBackground(new Color(49, 50, 68));
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(30);
        table.getTableHeader().setBackground(new Color(30, 30, 46));
        table.getTableHeader().setForeground(new Color(203, 166, 247));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.setSelectionBackground(new Color(69, 71, 90));
        table.setGridColor(new Color(69, 71, 90));

        // Center semua kolom
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Warna baris ranking teratas
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            Color[] rowColors = {
                new Color(250, 219, 96, 40),   // rank 1 - gold
                new Color(166, 173, 200, 30),  // rank 2 - silver
                new Color(250, 179, 135, 30),  // rank 3 - bronze
                new Color(49, 50, 68),
                new Color(49, 50, 68)
            };
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                setHorizontalAlignment(SwingConstants.CENTER);
                setForeground(Color.WHITE);
                setBackground(row < rowColors.length ? rowColors[row] : new Color(49, 50, 68));
                if (sel) setBackground(new Color(69, 71, 90));
                return this;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(49, 50, 68));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Tombol tutup
        JButton btnClose = new JButton("Tutup");
        btnClose.setBackground(new Color(49, 50, 68));
        btnClose.setForeground(Color.WHITE);
        btnClose.setFont(new Font("Arial", Font.BOLD, 13));
        btnClose.setFocusPainted(false);
        btnClose.addActionListener(e -> this.dispose());
        mainPanel.add(btnClose, BorderLayout.SOUTH);

        add(mainPanel);
    }
}
