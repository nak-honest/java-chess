package chess.domain.game.state;

import chess.domain.board.ChessBoard;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.domain.position.StartEndPosition;

import java.util.Map;

public class EndState implements State {
    @Override
    public State start() {
        throw new IllegalArgumentException("게임이 이미 종료되었습니다.");
    }

    @Override
    public State load(Color currentTurn, ChessBoard board, Map<Position, Piece> loadedBoard) {
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

    @Override
    public Color getCurrentTurn() {
        throw new IllegalArgumentException("게임이 이미 종료되었습니다.");
    }
}
