package chess.service;

import chess.dao.BoardDao;
import chess.dao.GameDao;
import chess.db.JdbcConnection;
import chess.domain.game.ChessGame;

import java.sql.Connection;
import java.sql.SQLException;

public class GameService {

    public void saveGame(ChessGame chessGame) {
        try {
            Connection connection = JdbcConnection.getConnection();
            connection.setAutoCommit(false);
            GameDao gameDao = new GameDao(connection);
            BoardDao boardDao = new BoardDao(connection);
            int gameId = gameDao.save(chessGame.getState())
                    .orElseThrow(() -> new RuntimeException("ID가 존재하지 않습니다."));
            boardDao.save(gameId, chessGame.getPieces());
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
