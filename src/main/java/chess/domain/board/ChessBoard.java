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

// TODO: 하위 문제들을 객체로 분리하는 것 고려해보기
public class ChessBoard {
    private final Map<Position, Piece> pieces;

    public ChessBoard(Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public Map<Position, Piece> getPieces() {
        return Collections.unmodifiableMap(pieces);
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
        validateStartFriendly(startEndPosition.getStart(), currentTurn);
        validateEndNotFriendly(startEndPosition.getEnd(), currentTurn);
    }

    private void validateStartFriendly(Position startPosition, Color friendlyColor) {
        if (isEmpty(startPosition) || isEnemy(startPosition, friendlyColor)) {
            throw new IllegalArgumentException("시작 위치에 아군 체스말이 존재해야 합니다.");
        }
    }

    private void validateEndNotFriendly(Position endPosition, Color currentTurn) {
        if (isNotEmpty(endPosition) && isFriendly(endPosition, currentTurn)) {
            throw new IllegalArgumentException("도착 위치에 아군 체스말이 존재할 수 없습니다.");
        }
    }

    private boolean isEmpty(Position position) {
        return getPiece(position) == Empty.getInstance();
    }

    private boolean isNotEmpty(Position position) {
        return !isEmpty(position);
    }

    private boolean isFriendly(Position position, Color friendlyColor) {
        Piece startPiece = getPiece(position);
        return startPiece.isColor(friendlyColor);
    }

    private boolean isEnemy(Position position, Color friendlyColor) {
        return !isFriendly(position, friendlyColor);
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
}
