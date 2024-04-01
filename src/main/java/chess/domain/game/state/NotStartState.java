package chess.domain.game.state;

import chess.domain.board.ChessBoard;
import chess.domain.position.StartEndPosition;

public class NotStartState implements State {
    @Override
    public State start() {
        return ProcessState.createOnStart();
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
}
