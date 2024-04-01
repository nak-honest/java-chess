package chess.domain.board;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.Rook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StartAndEndValidatorTest {
    @DisplayName("시작 말이 아군이 아닌 경우 예외를 발생시킨다.")
    @Test
    void startEmptyExceptionTest() {
        // given
        StartAndEndValidator validator = StartAndEndValidator.getInstance();
        Piece startPiece = Rook.from(Color.WHITE);
        Piece endPiece = Rook.from(Color.WHITE);
        Color currentTurn = Color.BLACK;

        // when & then
        assertThatThrownBy(() -> validator.validate(startPiece, endPiece, currentTurn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작 위치에 아군 체스말이 존재해야 합니다.");
    }

    @DisplayName("도착 말이 아군인 경우 예외를 발생시킨다.")
    @Test
    void canNotAttack() {
        // given
        StartAndEndValidator validator = StartAndEndValidator.getInstance();
        Piece startPiece = Rook.from(Color.WHITE);
        Piece endPiece = Rook.from(Color.WHITE);
        Color currentTurn = Color.WHITE;

        // when & then
        assertThatThrownBy(() -> validator.validate(startPiece, endPiece, currentTurn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("도착 위치에 아군 체스말이 존재할 수 없습니다.");
    }
}
