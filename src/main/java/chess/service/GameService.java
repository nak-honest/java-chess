package chess.service;

import chess.dao.BoardDao;
import chess.dao.GameDao;
import chess.db.JdbcConnection;
import chess.domain.game.ChessGame;
import chess.entity.GameEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;

public class GameService {

    public void saveGame(ChessGame chessGame) {
        startTransaction(connection -> saveGame(chessGame, connection));
    }

    public void saveGame(ChessGame chessGame, Connection connection) {
        GameDao gameDao = new GameDao(connection);
        BoardDao boardDao = new BoardDao(connection);
        int gameId = gameDao.save(chessGame.currentTurn());
        boardDao.save(gameId, chessGame.getPieces());
    }

    public void loadRecentGame(ChessGame chessGame) {
        startTransaction(connection -> loadRecentGame(chessGame, connection));
    }

    private void loadRecentGame(ChessGame chessGame, Connection connection) {
        GameDao gameDao = new GameDao(connection);
        BoardDao boardDao = new BoardDao(connection);
        GameEntity gameEntity = gameDao.findRecentGame()
                .orElseThrow(() -> new IllegalArgumentException("최근 게임 내역이 없습니다."));
        chessGame.loadGame(gameEntity.currentTurn(), boardDao.findByGameId(gameEntity.id()));
    }

    public void deleteAllSave() {
        startTransaction(connection -> deleteAllSave(connection));
    }

    private void deleteAllSave(Connection connection) {
        GameDao gameDao = new GameDao(connection);
        BoardDao boardDao = new BoardDao(connection);
        boardDao.deleteAll();
        gameDao.deleteAll();
    }

    private void startTransaction(Consumer<Connection> consumer) {
        try {
            Connection connection = JdbcConnection.getConnection();
            connection.setAutoCommit(false);
            consumer.accept(connection);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}