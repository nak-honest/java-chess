package chess.domain.board;

import chess.domain.piece.Color;
import chess.domain.piece.Empty;
import chess.domain.piece.Piece;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import chess.domain.position.StartEndPosition;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessBoard {
    private final Map<Position, Piece> pieces;

    public ChessBoard(Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public void move(StartEndPosition startEndPosition, Color currentTurn) {
        validate(startEndPosition, currentTurn);
        if (getPiece(startEndPosition.getEnd()) == Empty.getInstance()) {
            passPiece(startEndPosition);
            return;
        }
        attackPiece(startEndPosition);
    }

    private void validate(StartEndPosition startEndPosition, Color currentTurn) {
        StartAndEndValidator validator = StartAndEndValidator.getInstance();
        Piece startPiece = getPiece(startEndPosition.getStart());
        Piece endPiece = getPiece(startEndPosition.getEnd());

        validator.validate(startPiece, endPiece, currentTurn);
    }

    private void passPiece(StartEndPosition startEndPosition) {
        Piece startPiece = getPiece(startEndPosition.getStart());
        validateObstacle(startPiece.findPassPathTaken(startEndPosition));

        exchange(startEndPosition, startPiece);
    }

    private void exchange(StartEndPosition startEndPosition, Piece startPiece) {
        Piece temp = getPiece(startEndPosition.getEnd());
        putPiece(startEndPosition.getEnd(), startPiece);
        putPiece(startEndPosition.getStart(), temp);
    }

    private void attackPiece(StartEndPosition startEndPosition) {
        Piece startPiece = getPiece(startEndPosition.getStart());
        validateObstacle(startPiece.findAttackPathTaken(startEndPosition));

        putPiece(startEndPosition.getEnd(), startPiece);
        putPiece(startEndPosition.getStart(), Empty.getInstance());
    }

    private void validateObstacle(List<Position> pathTaken) {
        if (isObstacleIn(pathTaken)) {
            throw new IllegalArgumentException("경로에 장애물이 있습니다.");
        }
    }

    private boolean isObstacleIn(List<Position> pathTaken) {
        return pathTaken.stream()
                .anyMatch(this::isNotEmpty);
    }

    private boolean isNotEmpty(Position position) {
        return getPiece(position) != Empty.getInstance();
    }

    private Piece getPiece(Position position) {
        return pieces.get(position);
    }

    private void putPiece(Position position, Piece piece) {
        pieces.put(position, piece);
    }

    public int countPiece(Piece counted) {
        return (int) pieces.values().stream()
                .filter(piece -> piece.equals(counted))
                .count();
    }

    public int countPieceAtFile(Piece counted, File file) {
        return (int) Arrays.stream(Rank.values())
                .map(rank -> new Position(file, rank))
                .filter(position -> counted.equals(pieces.get(position)))
                .count();
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "pieces=" + pieces +
                '}';
    }

    public Map<Position, Piece> getPieces() {
        return Collections.unmodifiableMap(pieces);
    }
}
