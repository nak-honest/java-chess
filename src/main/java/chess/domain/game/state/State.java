package chess.domain.game.state;

import chess.domain.board.ChessBoard;
import chess.domain.position.StartEndPosition;

public interface State {
    State start();

    State move(ChessBoard board, StartEndPosition startEndPosition);

    State end();

    boolean isProcess();
}
