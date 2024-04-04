CREATE DATABASE IF NOT EXISTS chess DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE chess;

CREATE TABLE IF NOT EXISTS game (
    id INT AUTO_INCREMENT,
    game_turn VARCHAR(20) NOT NULL,
    saved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS board (
    game_id INT,
    rank_position INT NOT NULL,
    file_position INT NOT NULL,
    piece_color VARCHAR(20) NOT NULL,
    piece_type VARCHAR(20) NOT NULL,
    PRIMARY KEY (game_id, rank_position, file_position),
    FOREIGN KEY (game_id) REFERENCES game(id)
);

# user에게 권한 부여
GRANT ALL PRIVILEGES ON `chess`.* TO 'user'@'%';
flush privileges;
