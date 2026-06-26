-- ============================================================
-- File    : schema.sql
-- Deskripsi: Setup database untuk proyek Tic-Tac-Toe
-- DBMS    : MySQL (bisa diadaptasi ke PostgreSQL / SQL Server)
-- ============================================================

CREATE DATABASE IF NOT EXISTS game_project;
USE game_project;

-- Satu tabel saja sesuai requirement
CREATE TABLE IF NOT EXISTS players (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    wins     INT DEFAULT 0,
    losses   INT DEFAULT 0,
    draws    INT DEFAULT 0,
    score    INT DEFAULT 0
);

-- Data sample untuk testing login
INSERT INTO players (username, password, wins, losses, draws, score)
VALUES
    ('student1', '12345', 0, 0, 0, 0),
    ('student2', '12345', 0, 0, 0, 0),
    ('student3', '12345', 0, 0, 0, 0),
    ('student4', '12345', 0, 0, 0, 0),
    ('student5', '12345', 0, 0, 0, 0);

-- ============================================================
-- Cara run:
-- 1. Buka MySQL CLI atau phpMyAdmin
-- 2. Jalankan file ini: SOURCE /path/to/schema.sql;
--    atau copy-paste isinya ke query editor
-- ============================================================
