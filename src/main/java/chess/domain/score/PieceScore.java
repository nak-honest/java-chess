package chess.domain.score;

import chess.domain.piece.Bishop;
import chess.domain.piece.Color;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;

import java.util.Arrays;
import java.util.Set;

public enum PieceScore {
    QUEEN(9, Set.of(Queen.from(Color.BLACK), Queen.from(Color.WHITE))),
    ROOK(5, Set.of(Rook.from(Color.BLACK), Rook.from(Color.WHITE))),
    BISHOP(3, Set.of(Bishop.from(Color.BLACK), Bishop.from(Color.WHITE))),
    KNIGHT(2.5, Set.of(Knight.from(Color.BLACK), Knight.from(Color.WHITE))),
    PAWN(1, Set.of(Pawn.from(Color.BLACK), Pawn.from(Color.WHITE))),
    KING(0, Set.of(King.from(Color.BLACK), King.from(Color.WHITE)));

    private final double score;
    private final Set<Piece> pieces;

    PieceScore(double score, Set<Piece> pieces) {
        this.score = score;
        this.pieces = pieces;
    }

    public static double getScore(Piece piece) {
        return Arrays.stream(values())
                .filter(pieceScore -> pieceScore.pieces.contains(piece))
                .map(pieceScore -> pieceScore.score)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 체스 말 입니다."));
    }
}
