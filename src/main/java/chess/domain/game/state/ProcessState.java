package chess.domain.game.state;

import chess.domain.board.ChessBoard;
import chess.domain.game.Turn;
import chess.domain.piece.Color;
import chess.domain.piece.King;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.domain.position.StartEndPosition;

import java.util.Map;

public class ProcessState implements State {
    private final Turn turn;

    private ProcessState(Turn turn) {
        this.turn = turn;
    }

    public static ProcessState createOnStart() {
        return new ProcessState(Turn.createOnStart());
    }

    public static ProcessState from(Color currentTurn) {
        return new ProcessState(Turn.from(currentTurn));
    }

    @Override
    public State start() {
        throw new IllegalArgumentException("게임이 이미 시작되었습니다.");
    }

    @Override
    public State load(Color currentTurn, ChessBoard board, Map<Position, Piece> loadedBoard) {
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
    public String toString() {
        return "ProcessState{" +
                "turn=" + turn +
                '}';
    }

    @Override
    public Color getCurrentTurn() {
        return turn.getCurrentTurn();
    }
}
