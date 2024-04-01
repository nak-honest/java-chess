package chess.domain.game.state;

import chess.domain.board.ChessBoard;
import chess.domain.position.StartEndPosition;

public class EndState implements State {
    @Override
    public State start() {
        throw new IllegalArgumentException("게임이 이미 종료되었습니다.");
    }

    @Override
    public State move(ChessBoard board, StartEndPosition startEndPosition) {
        throw new IllegalArgumentException("게임이 이미 종료되었습니다.");
    }

    @Override
    public State end() {
        throw new IllegalArgumentException("게임이 이미 종료되었습니다.");
    }

    @Override
    public boolean isProcess() {
        return false;
    }
}
