package chess.dao;

import chess.db.JdbcConnection;
import chess.domain.game.Turn;
import chess.domain.game.state.ProcessState;
import chess.domain.game.state.State;
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

    @DisplayName("ProcessState 저장 테스트")
    @ParameterizedTest()
    @MethodSource("provideProcessState")
    void saveProcessStateTest(State state) {
        // when
        gameDao.save(state);

        // then
        assertThat(gameDao.findRecent().get())
                .isEqualTo(state);
    }

    static List<State> provideProcessState() {
        return List.of(
                ProcessState.from(Turn.from(List.of(Color.WHITE, Color.BLACK))),
                ProcessState.from(Turn.from(List.of(Color.BLACK, Color.WHITE)))
        );
    }

    @DisplayName("가장 최근에 저장한 state를 찾는 테스트")
    @Test
    void findRecentTest() {
        // given
        State state = ProcessState.from(Turn.from(List.of(Color.WHITE, Color.BLACK)));
        gameDao.save(state);

        // when & then
        assertThat(gameDao.findRecent().get())
                .isEqualTo(state);
    }
}
