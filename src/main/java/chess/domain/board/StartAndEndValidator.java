package chess.domain.board;

import chess.domain.piece.Color;
import chess.domain.piece.Empty;
import chess.domain.piece.Piece;

class StartAndEndValidator {
    private static final StartAndEndValidator INSTANCE = new StartAndEndValidator();

    private StartAndEndValidator() {
    }

    public static StartAndEndValidator getInstance() {
        return INSTANCE;
    }

    void validate(Piece startPiece, Piece endPiece, Color friendlyColor) {
        validateStartFriendly(startPiece, friendlyColor);
        validateEndNotFriendly(endPiece, friendlyColor);
    }

    private void validateStartFriendly(Piece startPiece, Color friendlyColor) {
        if (startPiece == Empty.getInstance() || isEnemy(startPiece, friendlyColor)) {
            throw new IllegalArgumentException("시작 위치에 아군 체스말이 존재해야 합니다.");
        }
    }

    private void validateEndNotFriendly(Piece endPiece, Color currentTurn) {
        if (endPiece != Empty.getInstance() && isFriendly(endPiece, currentTurn)) {
            throw new IllegalArgumentException("도착 위치에 아군 체스말이 존재할 수 없습니다.");
        }
    }

    private boolean isFriendly(Piece piece, Color friendlyColor) {
        return piece.isColor(friendlyColor);
    }

    private boolean isEnemy(Piece piece, Color friendlyColor) {
        return !isFriendly(piece, friendlyColor);
    }
}
