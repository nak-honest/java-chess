package chess.domain.game;

import chess.domain.board.ChessBoard;
import chess.domain.board.PiecePositions;
import chess.domain.piece.Color;
import chess.domain.piece.King;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.domain.position.StartEndPosition;
import chess.domain.score.PieceScore;

import java.util.Map;

public class ChessGame {
    public static final double PAWN_PENALTY_SCORE = 0.5;
    private final Turn turn;
    private final ChessBoard board;

    public ChessGame(Turn turn, ChessBoard board) {
        this.turn = turn;
        this.board = board;
    }

    public static ChessGame createOnStart() {
        return new ChessGame(Turn.createOnStart(), PiecePositions.createBoard());
    }

    public void movePiece(StartEndPosition startEndPosition) {
        board.move(startEndPosition, turn.getCurrentTurn());
        turn.process();
    }

    public boolean isGameOver() {
        return isKingDead(Color.BLACK) || isKingDead(Color.WHITE);
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
        return PieceScore.status(board);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "turn=" + turn +
                ", board=" + board +
                '}';
    }

    public Map<Position, Piece> getPieces() {
        return board.getPieces();
    }
}
