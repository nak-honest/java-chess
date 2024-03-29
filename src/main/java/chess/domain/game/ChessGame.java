package chess.domain.game;

import chess.domain.board.ChessBoard;
import chess.domain.board.PiecePositions;
import chess.domain.piece.Bishop;
import chess.domain.piece.Color;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;
import chess.domain.position.Position;
import chess.domain.position.TerminalPosition;
import chess.domain.score.PieceScore;

import java.util.Map;
import java.util.Set;

public class ChessGame {
    private final Turn turn;
    private final ChessBoard board;

    public ChessGame(Turn turn, ChessBoard board) {
        this.turn = turn;
        this.board = board;
    }

    public static ChessGame createOnStart() {
        return new ChessGame(Turn.createOnStart(), PiecePositions.createBoard());
    }

    public void movePiece(TerminalPosition terminalPosition) {
        board.move(terminalPosition, turn.getCurrentTurn());
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

    public Map<Color, Double> calculateScore() {
        return Map.of(
                Color.BLACK, calculateScore(Color.BLACK),
                Color.WHITE, calculateScore(Color.WHITE)
        );
    }

    private double calculateScore(Color color) {
        Set<Piece> pieces = Set.of(
                Bishop.from(color), King.from(color), Knight.from(color),
                Pawn.from(color), Queen.from(color), Rook.from(color));

        return pieces.stream()
                .map(piece -> PieceScore.getScore(piece) * board.countPiece(piece))
                .reduce(0.0, Double::sum);
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
