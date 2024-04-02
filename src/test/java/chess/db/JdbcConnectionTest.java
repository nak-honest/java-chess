package chess.db;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

public class JdbcConnectionTest {
    @DisplayName("Jdbc 커넥션 연결 테스트")
    @Test
    void jdbcGetConnectionTest() {
        assertThatCode(() -> JdbcConnection.getConnection())
                .doesNotThrowAnyException();
    }
}
