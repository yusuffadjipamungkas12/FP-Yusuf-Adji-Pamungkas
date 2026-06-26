import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameFrame extends JFrame {

    private Player        currentPlayer;
    private PlayerService playerService;
    private GameLogic     gameLogic;

    private JButton[] buttons;  // 9 tombol papan
    private JLabel    lblStatus;
    private JButton   btnReset;
    private JButton   btnBack;

    private boolean gameOver = false;

    public GameFrame(Player player) {
        this.currentPlayer = player;
        this.playerService = new PlayerService();
        this.gameLogic     = new GameLogic();
        setupUI();
    }

    private void setupUI() {
        setTitle("Tic-Tac-Toe — Main Game");
        setSize(420, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(30, 30, 46));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ── Header ──────────────────────────────────────────────────
        JLabel lblTitle = new JLabel("TIC-TAC-TOE", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(new Color(203, 166, 247));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // ── Papan 3x3 ───────────────────────────────────────────────
        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 6, 6));
        boardPanel.setBackground(new Color(30, 30, 46));
        buttons = new JButton[9];

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 36));
            buttons[i].setBackground(new Color(49, 50, 68));
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setFocusPainted(false);
            buttons[i].setBorder(BorderFactory.createLineBorder(new Color(69, 71, 90), 2));
            final int idx = i;
            buttons[i].addActionListener(e -> handlePlayerMove(idx));
            boardPanel.add(buttons[i]);
        }
        mainPanel.add(boardPanel, BorderLayout.CENTER);

        // ── Status + tombol bawah ────────────────────────────────────
        JPanel bottomPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        bottomPanel.setBackground(new Color(30, 30, 46));

        lblStatus = new JLabel("Giliran kamu — klik kotak untuk bermain", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 13));
        lblStatus.setForeground(new Color(166, 173, 200));
        bottomPanel.add(lblStatus);

        btnReset = new JButton("🔄  Main Lagi");
        btnReset.setBackground(new Color(166, 227, 161));
        btnReset.setForeground(new Color(30, 30, 46));
        btnReset.setFont(new Font("Arial", Font.BOLD, 13));
        btnReset.setFocusPainted(false);
        bottomPanel.add(btnReset);

        btnBack = new JButton("← Kembali ke Menu");
        btnBack.setBackground(new Color(49, 50, 68));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Arial", Font.BOLD, 13));
        btnBack.setFocusPainted(false);
        bottomPanel.add(btnBack);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // ── Event tombol utilitas ────────────────────────────────────
        btnReset.addActionListener(e -> resetGame());

        btnBack.addActionListener(e -> {
            // Refresh data player dari DB sebelum balik ke menu
            playerService.refreshPlayer(currentPlayer);
            MainMenuFrame menuFrame = new MainMenuFrame(currentPlayer);
            menuFrame.setVisible(true);
            this.dispose();
        });
    }

    // ─── Giliran Pemain ───────────────────────────────────────────────
    private void handlePlayerMove(int index) {
        if (gameOver) return;

        boolean moved = gameLogic.makeMove(index, 'X');
        if (!moved) {
            lblStatus.setText("Kotak sudah terisi! Pilih yang lain.");
            return;
        }

        // Update tampilan tombol
        buttons[index].setText("X");
        buttons[index].setForeground(new Color(137, 180, 250));
        buttons[index].setEnabled(false);

        // Cek menang
        if (gameLogic.checkWinner('X')) {
            finishGame("WIN");
            return;
        }

        // Cek draw
        if (gameLogic.isDraw()) {
            finishGame("DRAW");
            return;
        }

        // Giliran komputer
        lblStatus.setText("Komputer sedang berpikir...");
        int compIndex = gameLogic.computerMove();

        if (compIndex != -1) {
            gameLogic.makeMove(compIndex, 'O');
            buttons[compIndex].setText("O");
            buttons[compIndex].setForeground(new Color(243, 139, 168));
            buttons[compIndex].setEnabled(false);

            // Cek komputer menang
            if (gameLogic.checkWinner('O')) {
                finishGame("LOSE");
                return;
            }

            // Cek draw setelah komputer bergerak
            if (gameLogic.isDraw()) {
                finishGame("DRAW");
                return;
            }
        }

        lblStatus.setText("Giliran kamu — klik kotak kosong.");
    }

    // ─── Selesai Game ─────────────────────────────────────────────────
    private void finishGame(String result) {
        gameOver = true;

        // Nonaktifkan semua tombol
        for (JButton btn : buttons) btn.setEnabled(false);

        // Tampilkan hasil
        String pesan;
        Color  warna;
        if (result.equals("WIN")) {
            pesan = "🎉 Kamu MENANG! +10 poin";
            warna = new Color(166, 227, 161);
        } else if (result.equals("LOSE")) {
            pesan = "😢 Kamu KALAH! Lebih baik lagi next time.";
            warna = new Color(243, 139, 168);
        } else {
            pesan = "🤝 SERI! +3 poin";
            warna = new Color(250, 179, 135);
        }

        lblStatus.setText(pesan);
        lblStatus.setForeground(warna);

        // Simpan ke database
        playerService.updateStatistics(currentPlayer, result);

        JOptionPane.showMessageDialog(this, pesan, "Hasil Game", JOptionPane.INFORMATION_MESSAGE);
    }

    // ─── Reset Game ───────────────────────────────────────────────────
    private void resetGame() {
        gameLogic.resetBoard();
        gameOver = false;

        for (JButton btn : buttons) {
            btn.setText("");
            btn.setEnabled(true);
            btn.setForeground(Color.WHITE);
        }

        lblStatus.setText("Giliran kamu — klik kotak untuk bermain");
        lblStatus.setForeground(new Color(166, 173, 200));
    }
}
