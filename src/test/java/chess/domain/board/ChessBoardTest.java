package chess.domain.board;

import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import chess.domain.position.TerminalPosition;
import chess.domain.square.Empty;
import chess.domain.square.Square;
import chess.domain.square.piece.Color;
import chess.domain.square.piece.Queen;
import chess.domain.square.piece.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ChessBoardTest {

    private static final Map<Position, Square> squares = new HashMap<>();

    @BeforeEach
    void setUp() {
        for (Rank rank : Rank.values()) {
            for (File file : File.values()) {
                squares.put(new Position(rank, file), Empty.getInstance());
            }
        }
    }

    @DisplayName("체스말을 움직일 때, 시작 위치에 아군 말이 존재하지 않는 경우 예외를 발생시킨다.")
    @Test
    void startEmptyExceptionTest() {
        // given
        ChessBoard chessBoard = new ChessBoard(squares);
        TerminalPosition terminalPosition = new TerminalPosition(new Position(Rank.FIRST, File.A), new Position(Rank.SECOND, File.B));
        Color currentTurn = Color.BLACK;

        // when & then
        assertThatThrownBy(() -> chessBoard.move(terminalPosition, currentTurn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작 위치에 아군 체스말이 존재해야 합니다.");
    }

    @DisplayName("체스말을 움직일 때, 도착 위치에 아군 말이 존재하는 경우 예외를 발생시킨다.")
    @Test
    void canNotAttack() {
        // given
        squares.put(new Position(Rank.FIRST, File.A), Rook.from(Color.WHITE));
        squares.put(new Position(Rank.SECOND, File.A), Rook.from(Color.WHITE));
        ChessBoard chessBoard = new ChessBoard(squares);
        TerminalPosition terminalPosition = new TerminalPosition(new Position(Rank.FIRST, File.A), new Position(Rank.SECOND, File.A));
        Color currentTurn = Color.WHITE;

        // when & then
        assertThatThrownBy(() -> chessBoard.move(terminalPosition, currentTurn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("도착 위치에 아군 체스말이 존재할 수 없습니다.");
    }

    @DisplayName("target이 비어있는 경우 체스말을 움직인다.")
    @Test
    void movePieceTest() {
        // given
        squares.put(new Position(Rank.FIRST, File.A), Rook.from(Color.WHITE));
        ChessBoard chessBoard = new ChessBoard(squares);
        Map<Position, Square> expected = provideEmptyBoard();
        expected.put(new Position(Rank.FIRST, File.B), Rook.from(Color.WHITE));

        // when
        chessBoard.move(new TerminalPosition(new Position(Rank.FIRST, File.A), new Position(Rank.FIRST, File.B)), Color.WHITE);

        // then
        assertThat(chessBoard.getSquares()).isEqualTo(expected);
    }

    @DisplayName("target이 체스말인 경우 공격한다.")
    @Test
    void moveAttackTest() {
        // given
        squares.put(new Position(Rank.FIRST, File.A), Rook.from(Color.WHITE));
        squares.put(new Position(Rank.FIRST, File.B), Queen.from(Color.BLACK));
        ChessBoard chessBoard = new ChessBoard(squares);
        Map<Position, Square> expected = provideEmptyBoard();
        expected.put(new Position(Rank.FIRST, File.B), Rook.from(Color.WHITE));

        // when
        chessBoard.move(new TerminalPosition(new Position(Rank.FIRST, File.A), new Position(Rank.FIRST, File.B)), Color.WHITE);

        // then
        assertThat(chessBoard.getSquares()).isEqualTo(expected);
    }


    static Map<Position, Square> provideEmptyBoard() {
        Map<Position, Square> squares = new HashMap<>();

        for (Rank rank : Rank.values()) {
            for (File file : File.values()) {
                squares.put(new Position(rank, file), Empty.getInstance());
            }
        }

        return squares;
    }
}
