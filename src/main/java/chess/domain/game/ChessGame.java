package chess.domain.game;

import chess.domain.board.ChessBoard;
import chess.domain.board.PiecePositions;
import chess.domain.game.state.NotStartState;
import chess.domain.game.state.State;
import chess.domain.piece.Color;
import chess.domain.piece.King;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.domain.position.StartEndPosition;
import chess.domain.score.PieceScore;

import java.util.Map;

public class ChessGame {
    private State state;
    private final ChessBoard board;

    private ChessGame(State state, ChessBoard board) {
        this.state = state;
        this.board = board;
    }

    public static ChessGame create() {
        return new ChessGame(new NotStartState(), PiecePositions.createBoard());
    }

    public void loadGame(Color currentTurn, Map<Position, Piece> loadedBoard) {
        state = state.load(currentTurn, board, loadedBoard);
    }

    public void startGame() {
        state = state.start();
    }

    public void movePiece(StartEndPosition startEndPosition) {
        state = state.move(board, startEndPosition);
    }

    public void endGame() {
        state = state.end();
    }

    public boolean isKingDead() {
        return isKingDead(Color.BLACK) || isKingDead(Color.WHITE);
    }

    public boolean isProcess() {
        return state.isProcess();
    }

    public Color winnerColor() {
        if (isKingDead(Color.BLACK)) {
            return Color.WHITE;
        }

        return Color.BLACK;
    }

    private boolean isKingDead(Color color) {
        return board.countPiece(King.from(color)) == 0;
    }

    public Map<Color, Double> status() {
        if (!state.isProcess()) {
            throw new IllegalArgumentException("게임이 진행중인 상태가 아닙니다.");
        }

        return PieceScore.status(board);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "state=" + state +
                ", board=" + board +
                '}';
    }

    public Map<Position, Piece> getPieces() {
        return board.getPieces();
    }

    public Color currentTurn() {
        return state.getCurrentTurn();
    }
}
