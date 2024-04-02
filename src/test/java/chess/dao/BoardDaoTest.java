package chess.dao;

import chess.db.JdbcConnection;
import chess.domain.board.ChessBoard;
import chess.domain.board.PiecePositions;
import chess.domain.piece.Color;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardDaoTest {
    private static Connection connection;
    private static BoardDao boardDao;
    private static GameDao gameDao;

    @BeforeEach
    void beforeEach() {
        try {
            connection = JdbcConnection.getConnection();
            connection.setAutoCommit(false);
            boardDao = new BoardDao(connection);
            gameDao = new GameDao(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void afterEach() {
        try {
            connection.rollback();
            boardDao = null;
            gameDao = null;
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("board 저장 테스트")
    @Test
    void saveEndStateTest() {
        // given
        int gameId = gameDao.save(Color.WHITE);
        ChessBoard chessBoard = PiecePositions.createBoard();

        // when
        boardDao.save(gameId, chessBoard.getPieces());

        // then
        assertThat(boardDao.findByGameId(gameId))
                .isEqualTo(chessBoard.getPieces());
    }

    @DisplayName("가장 최근에 저장한 board를 찾는 테스트")
    @Test
    void findRecentTest() {
        // given
        int gameId = gameDao.save(Color.WHITE);
        ChessBoard chessBoard = PiecePositions.createBoard();
        boardDao.save(gameId, chessBoard.getPieces());

        // when & then
        assertThat(boardDao.findByGameId(gameId))
                .isEqualTo(chessBoard.getPieces());
    }
}
