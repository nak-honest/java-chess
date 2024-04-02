package chess.dao;

import chess.db.JdbcConnection;
import chess.domain.piece.Color;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GameDaoTest {
    private static Connection connection;
    private static GameDao gameDao;

    @BeforeEach
    void beforeEach() {
        try {
            connection = JdbcConnection.getConnection();
            connection.setAutoCommit(false);
            gameDao = new GameDao(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void afterEach() {
        try {
            connection.rollback();
            gameDao = null;
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("게임 턴 저장 테스트")
    @ParameterizedTest()
    @MethodSource("provideCurrentTurn")
    void saveProcessStateTest(Color currentTurn) {
        // when
        gameDao.save(currentTurn);

        // then
        assertThat(gameDao.findRecentGame().get().currentTurn())
                .isEqualTo(currentTurn);
    }

    static List<Color> provideCurrentTurn() {
        return List.of(
                Color.WHITE,
                Color.BLACK
        );
    }

    @DisplayName("가장 최근에 저장한 게임의 턴을 찾는 테스트")
    @Test
    void findRecentTest() {
        // given
        Color currentTurn = Color.WHITE;
        gameDao.save(currentTurn);

        // when & then
        assertThat(gameDao.findRecentGame().get().currentTurn())
                .isEqualTo(currentTurn);
    }
}
