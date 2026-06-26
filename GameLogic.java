import java.util.ArrayList;
import java.util.Random;

public class GameLogic {

    private char[] board;
    private Random random;

    public GameLogic() {
        board  = new char[9];
        random = new Random();
        resetBoard();
    }

    // Reset semua sel menjadi kosong
    public void resetBoard() {
        for (int i = 0; i < board.length; i++) {
            board[i] = ' ';
        }
    }

    // Coba letakkan simbol di index tertentu
    // Return true jika berhasil, false jika sel sudah terisi atau index invalid
    public boolean makeMove(int index, char symbol) {
        if (index < 0 || index >= 9) return false;
        if (board[index] != ' ')     return false;
        board[index] = symbol;
        return true;
    }

    // Cek apakah simbol tertentu menang
    public boolean checkWinner(char symbol) {
        int[][] patterns = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // baris
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // kolom
            {0, 4, 8}, {2, 4, 6}               // diagonal
        };
        for (int[] p : patterns) {
            if (board[p[0]] == symbol && board[p[1]] == symbol && board[p[2]] == symbol) {
                return true;
            }
        }
        return false;
    }

    // Cek apakah papan penuh (draw)
    public boolean isDraw() {
        for (char c : board) {
            if (c == ' ') return false;
        }
        return true;
    }

    // Gerakan komputer — pakai strategi sederhana:
    // 1. Menang kalau bisa
    // 2. Blokir pemain kalau mau menang
    // 3. Ambil tengah
    // 4. Ambil sudut kosong
    // 5. Ambil sel acak
    public int computerMove() {
        // 1. Coba menang
        int win = findBestMove('O');
        if (win != -1) return win;

        // 2. Blokir pemain
        int block = findBestMove('X');
        if (block != -1) return block;

        // 3. Ambil tengah
        if (board[4] == ' ') return 4;

        // 4. Ambil sudut kosong
        int[] corners = {0, 2, 6, 8};
        ArrayList<Integer> emptyCorners = new ArrayList<Integer>();
        for (int c : corners) {
            if (board[c] == ' ') emptyCorners.add(c);
        }
        if (!emptyCorners.isEmpty()) {
            return emptyCorners.get(random.nextInt(emptyCorners.size()));
        }

        // 5. Ambil sel acak yang kosong
        ArrayList<Integer> emptyCells = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++) {
            if (board[i] == ' ') emptyCells.add(i);
        }
        if (!emptyCells.isEmpty()) {
            return emptyCells.get(random.nextInt(emptyCells.size()));
        }

        return -1; // papan penuh
    }

    // Cari posisi yang bisa langsung menang/blokir untuk simbol tertentu
    private int findBestMove(char symbol) {
        for (int i = 0; i < 9; i++) {
            if (board[i] == ' ') {
                board[i] = symbol;
                if (checkWinner(symbol)) {
                    board[i] = ' '; // kembalikan sebelum return
                    return i;
                }
                board[i] = ' ';
            }
        }
        return -1;
    }

    public char[] getBoard() {
        return board;
    }
}
