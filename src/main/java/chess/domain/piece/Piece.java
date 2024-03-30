package chess.domain.piece;

import chess.domain.movement.Movements;
import chess.domain.position.Position;
import chess.domain.position.StartEndPosition;

import java.util.List;
import java.util.Objects;

public abstract class Piece {
    private final Color color;
    // TODO: protected 개선
    protected final Movements movements;

    public Piece(Color color, Movements movements) {
        this.color = color;
        this.movements = movements;
    }

    public abstract List<Position> findPassPathTaken(StartEndPosition startEndPosition);

    public abstract List<Position> findAttackPathTaken(StartEndPosition startEndPosition);

    public boolean isColor(Color color) {
        return this.color == color;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "color=" + color +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Piece piece = (Piece) o;

        return color == piece.color;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(color);
    }

    public Color getColor() {
        return color;
    }
}
