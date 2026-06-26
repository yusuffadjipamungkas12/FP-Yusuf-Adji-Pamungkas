import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PlayerService {

    // ─────────────────────────────────────────────
    // LOGIN
    // ─────────────────────────────────────────────
    public Player login(String username, String password) {
        String sql = "SELECT * FROM players WHERE username = ? AND password = ?";
        try {
            Connection        conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int    id     = rs.getInt("id");
                String uname  = rs.getString("username");
                int    wins   = rs.getInt("wins");
                int    losses = rs.getInt("losses");
                int    draws  = rs.getInt("draws");
                int    score  = rs.getInt("score");
                return new Player(id, uname, wins, losses, draws, score);
            }
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
        }
        return null;
    }

    // ─────────────────────────────────────────────
    // UPDATE STATISTIK SETELAH GAME SELESAI
    // result: "WIN" | "LOSE" | "DRAW"
    // ─────────────────────────────────────────────
    public void updateStatistics(Player player, String result) {
        int    additionalScore = 0;
        String sql             = "";

        if (result.equalsIgnoreCase("WIN")) {
            additionalScore = 10;
            sql = "UPDATE players SET wins = wins + 1, score = score + ? WHERE id = ?";
        } else if (result.equalsIgnoreCase("LOSE")) {
            additionalScore = 0;
            sql = "UPDATE players SET losses = losses + 1, score = score + ? WHERE id = ?";
        } else if (result.equalsIgnoreCase("DRAW")) {
            additionalScore = 3;
            sql = "UPDATE players SET draws = draws + 1, score = score + ? WHERE id = ?";
        }

        try {
            Connection        conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, additionalScore);
            stmt.setInt(2, player.getId());
            stmt.executeUpdate();

            // Refresh nilai di object Player supaya StatisticsFrame akurat
            refreshPlayer(player);
        } catch (Exception e) {
            System.out.println("Update statistics error: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    // REFRESH DATA PLAYER DARI DB
    // ─────────────────────────────────────────────
    public void refreshPlayer(Player player) {
        String sql = "SELECT * FROM players WHERE id = ?";
        try {
            Connection        conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, player.getId());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                player.setWins(rs.getInt("wins"));
                player.setLosses(rs.getInt("losses"));
                player.setDraws(rs.getInt("draws"));
                player.setScore(rs.getInt("score"));
            }
        } catch (Exception e) {
            System.out.println("Refresh player error: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    // TOP 5 SCORERS
    // ─────────────────────────────────────────────
    public ArrayList<Player> getTopFiveScorers() {
        ArrayList<Player> list = new ArrayList<Player>();
        // Gunakan LIMIT 5 untuk MySQL/PostgreSQL
        // Untuk SQL Server ganti dengan: SELECT TOP 5 ...
        String sql = "SELECT * FROM players ORDER BY score DESC, wins DESC LIMIT 5";

        try {
            Connection        conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet         rs   = stmt.executeQuery();

            while (rs.next()) {
                int    id     = rs.getInt("id");
                String uname  = rs.getString("username");
                int    wins   = rs.getInt("wins");
                int    losses = rs.getInt("losses");
                int    draws  = rs.getInt("draws");
                int    score  = rs.getInt("score");
                list.add(new Player(id, uname, wins, losses, draws, score));
            }
        } catch (Exception e) {
            System.out.println("Get top scorers error: " + e.getMessage());
        }
        return list;
    }
}
