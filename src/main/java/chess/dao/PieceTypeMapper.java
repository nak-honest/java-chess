package chess.dao;

import chess.domain.piece.Bishop;
import chess.domain.piece.Color;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

public enum PieceTypeMapper {
    BISHOP(Bishop.class::isInstance, Bishop::from),
    KING(King.class::isInstance, King::from),
    KNIGHT(Knight.class::isInstance, Knight::from),
    PAWN(Pawn.class::isInstance, Pawn::from),
    QUEEN(Queen.class::isInstance, Queen::from),
    ROOK(Rook.class::isInstance, Rook::from);

    private final Predicate<Piece> matcher;
    private final Function<Color, Piece> pieceCreator;

    PieceTypeMapper(Predicate<Piece> matcher, Function<Color, Piece> pieceCreator) {
        this.matcher = matcher;
        this.pieceCreator = pieceCreator;
    }

    public static PieceTypeMapper from(Piece piece) {
        return Arrays.stream(values())
                .filter(pieceTypeMapper -> pieceTypeMapper.matcher.test(piece))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 PieceTypeMapper입니다."));
    }
}
