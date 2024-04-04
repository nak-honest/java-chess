package chess.entity;

import chess.domain.piece.Color;

public class GameEntity {
    private final int id;
    private final Color currentTurn;

    public GameEntity(int id, Color currentTurn) {
        this.id = id;
        this.currentTurn = currentTurn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameEntity that = (GameEntity) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public int getId() {
        return id;
    }

    public Color getCurrentTurn() {
        return currentTurn;
    }
}
