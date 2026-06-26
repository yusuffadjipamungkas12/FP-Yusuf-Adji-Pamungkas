Simple Tic-Tac-Toe Game with Java Swing, Login, and Statistics
Student Information
Field	Value
Name	(isi nama kamu)
Student ID	(isi NRP kamu)
Class	ES234211 – Programming Fundamental
---
Project Description
Aplikasi game Tic-Tac-Toe sederhana berbasis Java Swing GUI. Pemain login menggunakan akun yang tersimpan di database MySQL, lalu bermain melawan komputer (AI sederhana). Setiap hasil permainan (menang/kalah/seri) otomatis tercatat ke database dan bisa dilihat di halaman statistik maupun leaderboard Top 5 Scorers.
---
Features
Login — autentikasi username & password dari database
Game Tic-Tac-Toe — papan 3×3 berbasis Swing, pemain vs komputer
AI Komputer — strategi sederhana: menang jika bisa → blokir pemain → tengah → sudut → acak
Statistik Personal — menang, kalah, seri, dan total skor
Top 5 Scorers — data live dari database ditampilkan dengan JTable
Skor — Menang +10, Seri +3, Kalah +0
---
Database
Item	Detail
DBMS	MySQL
Database	`game_project`
Tabel	`players` (1 tabel saja)
Skema Tabel
Kolom	Tipe	Keterangan
id	INT AUTO_INCREMENT	Primary Key
username	VARCHAR(50) UNIQUE	Username login
password	VARCHAR(100)	Password login
wins	INT DEFAULT 0	Jumlah menang
losses	INT DEFAULT 0	Jumlah kalah
draws	INT DEFAULT 0	Jumlah seri
score	INT DEFAULT 0	Total skor
---
How to Run
Prasyarat
Java JDK 8 atau lebih baru
MySQL Server aktif
MySQL Connector/J (driver JDBC) — download di https://dev.mysql.com/downloads/connector/j/
Langkah-Langkah
1. Setup Database
```sql
-- Buka MySQL CLI atau phpMyAdmin, lalu:
SOURCE database/schema.sql;
-- atau jalankan isi file schema.sql secara manual
```
2. Tambahkan JDBC Driver ke Project
Download `mysql-connector-j-x.x.x.jar`
Di IntelliJ: File → Project Structure → Libraries → tambahkan jar
Di VS Code / command line: taruh jar di folder `lib/` lalu compile dengan classpath
3. Sesuaikan Konfigurasi Database
Edit file `src/DatabaseManager.java`:
```java
private static final String URL      = "jdbc:mysql://localhost:3306/game_project";
private static final String USER     = "root";      // sesuaikan
private static final String PASSWORD = "";           // sesuaikan
```
4. Compile dan Run
```bash
# Dari folder root project (pastikan JDBC jar ada di lib/)
javac -cp ".;lib/mysql-connector-j-*.jar" src/*.java -d out/
java  -cp ".;lib/mysql-connector-j-*.jar;out/" Main
```
5. Login
Gunakan akun sample: username `student1`, password `12345`
---
Class Explanation
Class	Tanggung Jawab
`Main`	Entry point — membuka LoginFrame
`DatabaseManager`	Mengelola koneksi JDBC ke MySQL
`Player`	Model data pemain (id, username, stats)
`PlayerService`	Operasi DB: login, update statistik, top 5
`GameLogic`	Logika game: validasi move, cek menang/seri, AI komputer
`LoginFrame`	Window login dengan username & password
`MainMenuFrame`	Menu utama setelah login
`GameFrame`	Window bermain Tic-Tac-Toe
`StatisticsFrame`	Statistik personal pemain
`TopScorersFrame`	Leaderboard Top 5 menggunakan JTable
---
Screenshots
(tambahkan screenshots di sini setelah program berjalan)
Login Window	Main Menu	Game Window
![login](screenshots/login-window.png)	![menu](screenshots/main-menu.png)	![game](screenshots/game-window.png)

Statistics	Top 5 Scorers
![stats](screenshots/statistics-window.png)	![top5](screenshots/top-scorers-window.png)
---
Video Link
YouTube: (isi link setelah upload)
---
GitHub Link
(isi link repository kamu)
