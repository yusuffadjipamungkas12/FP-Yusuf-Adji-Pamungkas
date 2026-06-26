import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenuFrame extends JFrame {

    private Player  currentPlayer;
    private JButton btnStartGame;
    private JButton btnStatistics;
    private JButton btnTopScorers;
    private JButton btnExit;

    public MainMenuFrame(Player player) {
        this.currentPlayer = player;
        setupUI();
    }

    private void setupUI() {
        setTitle("Tic-Tac-Toe — Main Menu");
        setSize(380, 340);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 46));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 40, 8, 40);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        // Sambutan
        JLabel lblWelcome = new JLabel("Halo, " + currentPlayer.getUsername() + "!", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 18));
        lblWelcome.setForeground(new Color(203, 166, 247));
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblWelcome, gbc);

        JLabel lblSkor = new JLabel("Skor kamu: " + currentPlayer.getScore(), SwingConstants.CENTER);
        lblSkor.setFont(new Font("Arial", Font.PLAIN, 13));
        lblSkor.setForeground(new Color(166, 173, 200));
        gbc.gridy = 1;
        panel.add(lblSkor, gbc);

        // Tombol-tombol
        btnStartGame   = buatTombol("▶  Mulai Game");
        btnStatistics  = buatTombol("📊  Statistik Saya");
        btnTopScorers  = buatTombol("🏆  Top 5 Scorers");
        btnExit        = buatTombol("✖  Keluar");
        btnExit.setBackground(new Color(243, 139, 168));

        gbc.gridy = 2; panel.add(btnStartGame,  gbc);
        gbc.gridy = 3; panel.add(btnStatistics,  gbc);
        gbc.gridy = 4; panel.add(btnTopScorers,  gbc);
        gbc.gridy = 5; panel.add(btnExit,         gbc);

        add(panel);

        // ─── Event Handling ───────────────────────────────────────────
        btnStartGame.addActionListener(e -> {
            GameFrame gameFrame = new GameFrame(currentPlayer);
            gameFrame.setVisible(true);
            this.dispose();
        });

        btnStatistics.addActionListener(e -> {
            StatisticsFrame statsFrame = new StatisticsFrame(currentPlayer);
            statsFrame.setVisible(true);
        });

        btnTopScorers.addActionListener(e -> {
            TopScorersFrame topFrame = new TopScorersFrame();
            topFrame.setVisible(true);
        });

        btnExit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Yakin mau keluar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    private JButton buatTombol(String teks) {
        JButton btn = new JButton(teks);
        btn.setBackground(new Color(49, 50, 68));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return btn;
    }
}
