package chess.domain.piece;

import chess.domain.movement.Movements;
import chess.domain.movement.MovementsFactory;
import chess.domain.movement.UnitMovement;
import chess.domain.position.Position;
import chess.domain.position.StartEndPosition;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Queen extends Piece {
    private static final int MAX_MOVE_COUNT = 8;
    private static final Set<UnitMovement> COMMON_UNIT_MOVEMENTS =
            Stream.concat(MovementsFactory.createStraight().stream(), MovementsFactory.createDiagonal().stream())
                    .collect(Collectors.toSet());
    private static final Movements COMMON_MOVEMENTS = new Movements(COMMON_UNIT_MOVEMENTS, COMMON_UNIT_MOVEMENTS);
    private static final Map<Color, Queen> QUEEN_POOL = Map.of(
            Color.BLACK, new Queen(Color.BLACK),
            Color.WHITE, new Queen(Color.WHITE));

    private Queen(Color color) {
        super(color);
    }

    public static Queen from(Color color) {
        return QUEEN_POOL.get(color);
    }

    @Override
    public List<Position> findPassPathTaken(StartEndPosition startEndPosition) {
        return COMMON_MOVEMENTS.findPassPathTaken(startEndPosition, MAX_MOVE_COUNT);
    }

    @Override
    public List<Position> findAttackPathTaken(StartEndPosition startEndPosition) {
        return COMMON_MOVEMENTS.findAttackPathTaken(startEndPosition, MAX_MOVE_COUNT);
    }
}
