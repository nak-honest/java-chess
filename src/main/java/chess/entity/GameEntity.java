package chess.entity;

import chess.domain.piece.Color;

public record GameEntity(int id, Color currentTurn) {
}
