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

# 루트 계정 생성(권한 없는 루트 계정이 생성될 때 이를 해결)
CREATE USER 'root'@'%' IDENTIFIED BY 'root';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;
flush privileges;

# 외부에서 도커로 접속할 수 있도록 변경
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';
flush privileges;
