package chess.domain.score;

import chess.domain.board.ChessBoard;
import chess.domain.piece.Bishop;
import chess.domain.piece.Color;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;
import chess.domain.position.File;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public enum PieceScore {
    QUEEN_SCORE(9, Set.of(Queen.from(Color.BLACK), Queen.from(Color.WHITE))),
    ROOK_SCORE(5, Set.of(Rook.from(Color.BLACK), Rook.from(Color.WHITE))),
    BISHOP_SCORE(3, Set.of(Bishop.from(Color.BLACK), Bishop.from(Color.WHITE))),
    KNIGHT_SCORE(2.5, Set.of(Knight.from(Color.BLACK), Knight.from(Color.WHITE))),
    PAWN_SCORE(1, Set.of(Pawn.from(Color.BLACK), Pawn.from(Color.WHITE))),
    KING_SCORE(0, Set.of(King.from(Color.BLACK), King.from(Color.WHITE)));

    private final double score;
    private final Set<Piece> pieces;

    PieceScore(double score, Set<Piece> pieces) {
        this.score = score;
        this.pieces = pieces;
    }

    public Map<Color, Double> calculateScore(ChessBoard board) {
        return pieces.stream()
                .collect(Collectors.toMap(Piece::getColor, piece -> calculateTotalPieceScore(piece, board)));
    }

    private Double calculateTotalPieceScore(Piece piece, ChessBoard board) {
        if (this == PAWN_SCORE) {
            return calculatePawnScore(piece, board);
        }

        return score * board.countPiece(piece);
    }

    private double calculatePawnScore(Piece piece, ChessBoard board) {
        return Arrays.stream(File.values())
                .map(file -> calculatePawnScore(board.countPieceAtFile(piece, file)))
                .reduce(0.0, Double::sum);
    }

    private double calculatePawnScore(int pawnCountAtSameFile) {
        if (pawnCountAtSameFile > 1) {
            return 0.5 * pawnCountAtSameFile;
        }

        return score * pawnCountAtSameFile;
    }

    public static Map<Color, Double> status(ChessBoard board) {
        return Arrays.stream(values())
                .map(pieceScore -> pieceScore.calculateScore(board))
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingDouble(Map.Entry::getValue)));
    }
}
