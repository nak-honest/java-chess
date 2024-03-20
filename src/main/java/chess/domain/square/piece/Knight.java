package chess.domain.square.piece;

import chess.domain.position.Path;
import chess.domain.position.Position;
import chess.domain.square.Square;

import java.util.Map;

public class Knight extends Piece {
    private static final Map<Color, Knight> KNIGHT_POOL = Map.of(
            Color.WHITE, new Knight(Color.WHITE),
            Color.BLACK, new Knight(Color.BLACK));
    public static final int MIN_MOVABLE_DIFF = 1;
    public static final int MAX_MOVABLE_DIFF = 2;

    private Knight(Color color) {
        super(color);
    }

    public static Knight from(Color color) {
        return KNIGHT_POOL.get(color);
    }

    @Override
    protected boolean isValidPath(Path path) {
        return (path.calculateRankDiff() == MIN_MOVABLE_DIFF && path.calculateFileDiff() == MAX_MOVABLE_DIFF) ||
                (path.calculateRankDiff() == MAX_MOVABLE_DIFF && path.calculateFileDiff() == MIN_MOVABLE_DIFF);
    }

    @Override
    protected boolean isNotObstructed(Path path, Map<Position, Square> board) {
        return true;
    }

    @Override
    protected void move() {
    }
}