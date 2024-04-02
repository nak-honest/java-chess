package chess.domain.game.state;

import chess.domain.board.ChessBoard;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.domain.position.StartEndPosition;

import java.util.Map;

public class NotStartState implements State {
    @Override
    public State start() {
        return ProcessState.createOnStart();
    }

    @Override
    public State load(Color currentTurn, ChessBoard board, Map<Position, Piece> loadedBoard) {
        board.loadBoard(loadedBoard);
        return ProcessState.from(currentTurn);
    }

    @Override
    public State move(ChessBoard board, StartEndPosition startEndPosition) {
        throw new IllegalArgumentException("아직 게임이 시작되지 않았습니다.");
    }

    @Override
    public State end() {
        return new EndState();
    }

    @Override
    public boolean isProcess() {
        return false;
    }

    @Override
    public Color getCurrentTurn() {
        throw new IllegalArgumentException("아직 게임이 시작되지 않았습니다.");
    }
}
