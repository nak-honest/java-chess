package chess.domain.game.state;

import chess.domain.board.ChessBoard;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.domain.position.StartEndPosition;

import java.util.Map;

public interface State {
    State start();

    State load(Color currentTurn, ChessBoard board, Map<Position, Piece> loadedBoard);

    State move(ChessBoard board, StartEndPosition startEndPosition);

    State end();

    boolean isProcess();

    Color getCurrentTurn();
}
