package chess.domain.game.state;

import chess.domain.board.ChessBoard;
import chess.domain.game.Turn;
import chess.domain.piece.Color;
import chess.domain.piece.King;
import chess.domain.position.StartEndPosition;

import java.util.Objects;

public class ProcessState implements State {
    private final Turn turn;

    private ProcessState(Turn turn) {
        this.turn = turn;
    }

    public static ProcessState createOnStart() {
        return new ProcessState(Turn.createOnStart());
    }

    public static ProcessState from(Turn turn) {
        return new ProcessState(turn);
    }

    @Override
    public State start() {
        throw new IllegalArgumentException("게임이 이미 시작되었습니다.");
    }

    @Override
    public State move(ChessBoard board, StartEndPosition startEndPosition) {
        board.move(startEndPosition, turn.getCurrentTurn());
        if (isKingDead(board)) {
            return new EndState();
        }

        turn.process();
        return this;
    }

    private boolean isKingDead(ChessBoard board) {
        return board.countPiece(King.from(Color.BLACK)) == 0 || board.countPiece(King.from(Color.WHITE)) == 0;
    }

    @Override
    public State end() {
        return new EndState();
    }

    @Override
    public boolean isProcess() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProcessState that = (ProcessState) o;

        return Objects.equals(turn, that.turn);
    }

    @Override
    public int hashCode() {
        return turn != null ? turn.hashCode() : 0;
    }
}
