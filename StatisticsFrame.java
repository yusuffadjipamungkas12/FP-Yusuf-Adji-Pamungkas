import javax.swing.*;
import java.awt.*;

public class StatisticsFrame extends JFrame {

    private Player        currentPlayer;
    private PlayerService playerService;

    public StatisticsFrame(Player player) {
        this.currentPlayer = player;
        this.playerService = new PlayerService();
        // Refresh dari DB supaya data paling baru
        playerService.refreshPlayer(currentPlayer);
        setupUI();
    }

    private void setupUI() {
        setTitle("Statistik — " + currentPlayer.getUsername());
        setSize(340, 380);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 46));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 20, 8, 20);
        gbc.fill   = GridBagConstraints.HORIZONTAL;
        gbc.gridx  = 0;

        // Judul
        JLabel lblTitle = new JLabel("📊 Statistik Saya", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(new Color(203, 166, 247));
        gbc.gridy = 0;
        panel.add(lblTitle, gbc);

        // Nama pemain
        JLabel lblName = new JLabel("Pemain: " + currentPlayer.getUsername(), SwingConstants.CENTER);
        lblName.setFont(new Font("Arial", Font.PLAIN, 14));
        lblName.setForeground(new Color(166, 173, 200));
        gbc.gridy = 1;
        panel.add(lblName, gbc);

        // Garis pemisah
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(69, 71, 90));
        gbc.gridy = 2;
        panel.add(sep, gbc);

        // Kartu statistik
        gbc.gridy = 3; panel.add(buatKartu("🏆  Total Skor",  String.valueOf(currentPlayer.getScore()),  new Color(250, 219, 96)), gbc);
        gbc.gridy = 4; panel.add(buatKartu("✅  Menang",       String.valueOf(currentPlayer.getWins()),   new Color(166, 227, 161)), gbc);
        gbc.gridy = 5; panel.add(buatKartu("❌  Kalah",        String.valueOf(currentPlayer.getLosses()), new Color(243, 139, 168)), gbc);
        gbc.gridy = 6; panel.add(buatKartu("🤝  Seri",         String.valueOf(currentPlayer.getDraws()),  new Color(250, 179, 135)), gbc);

        // Tombol tutup
        JButton btnClose = new JButton("Tutup");
        btnClose.setBackground(new Color(49, 50, 68));
        btnClose.setForeground(Color.WHITE);
        btnClose.setFont(new Font("Arial", Font.BOLD, 13));
        btnClose.setFocusPainted(false);
        gbc.gridy = 7;
        panel.add(btnClose, gbc);

        btnClose.addActionListener(e -> this.dispose());

        add(panel);
    }

    private JPanel buatKartu(String label, String nilai, Color warna) {
        JPanel kartu = new JPanel(new BorderLayout());
        kartu.setBackground(new Color(49, 50, 68));
        kartu.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(warna, 2),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));

        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        lblLabel.setForeground(Color.WHITE);

        JLabel lblNilai = new JLabel(nilai, SwingConstants.RIGHT);
        lblNilai.setFont(new Font("Arial", Font.BOLD, 16));
        lblNilai.setForeground(warna);

        kartu.add(lblLabel, BorderLayout.WEST);
        kartu.add(lblNilai, BorderLayout.EAST);
        return kartu;
    }
}
