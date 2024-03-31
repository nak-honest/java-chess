package chess.domain.piece;

import chess.domain.position.Position;
import chess.domain.position.StartEndPosition;

import java.util.List;

public class Empty extends Piece {
    private static final Empty INSTANCE = new Empty();

    private Empty() {
        super(null);
    }

    public static Empty getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Position> findPassPathTaken(StartEndPosition startEndPosition) {
        throw new IllegalArgumentException("시작 위치가 비어있습니다.");
    }

    @Override
    public List<Position> findAttackPathTaken(StartEndPosition startEndPosition) {
        throw new IllegalArgumentException("시작 위치가 비어있습니다.");
    }
}
