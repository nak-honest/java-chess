package chess.service;

import chess.dao.BoardDao;
import chess.dao.GameDao;
import chess.db.JdbcConnection;
import chess.domain.game.ChessGame;
import chess.entity.GameEntity;

import java.sql.Connection;
import java.sql.SQLException;

public class GameService {

    public void saveGame(ChessGame chessGame) {
        try {
            Connection connection = JdbcConnection.getConnection();
            connection.setAutoCommit(false);
            GameDao gameDao = new GameDao(connection);
            BoardDao boardDao = new BoardDao(connection);
            int gameId = gameDao.save(chessGame.currentTurn());
            boardDao.save(gameId, chessGame.getPieces());
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadRecentGame(ChessGame chessGame) {
        try {
            Connection connection = JdbcConnection.getConnection();
            connection.setAutoCommit(true);
            GameDao gameDao = new GameDao(connection);
            BoardDao boardDao = new BoardDao(connection);
            GameEntity gameEntity = gameDao.findRecentGame().orElseThrow(() -> new IllegalArgumentException("최근 게임 내역이 없습니다."));
            chessGame.loadGame(gameEntity.currentTurn(), boardDao.findByGameId(gameEntity.id()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAllSave() {
        try {
            Connection connection = JdbcConnection.getConnection();
            connection.setAutoCommit(false);
            GameDao gameDao = new GameDao(connection);
            BoardDao boardDao = new BoardDao(connection);
            boardDao.deleteAll();
            gameDao.deleteAll();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
