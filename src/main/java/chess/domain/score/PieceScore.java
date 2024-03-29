package chess.domain.score;

// 각 말의 점수는 queen은 9점, rook은 5점, bishop은 3점, knight는 2.5점, king은 0점이다.
public enum PieceScore {
    QUEEN(9),
    ROOK(5),
    BISHOP(3),
    KNIGHT(2.5),
    PAWN(1),
    KING(0);

    private final double score;

    PieceScore(double score) {
        this.score = score;
    }
}
