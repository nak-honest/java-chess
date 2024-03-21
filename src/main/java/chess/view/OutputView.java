package chess.view;

import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import chess.domain.square.Empty;
import chess.domain.square.Square;
import chess.domain.square.piece.Bishop;
import chess.domain.square.piece.Color;
import chess.domain.square.piece.King;
import chess.domain.square.piece.Knight;
import chess.domain.square.piece.Pawn;
import chess.domain.square.piece.Queen;
import chess.domain.square.piece.Rook;

import java.util.HashMap;
import java.util.Map;

public class OutputView {
    private static final OutputView INSTANCE = new OutputView();
    private static final Map<Square, String> BLACK_SQUARE_VIEWS = Map.of(
            Pawn.of(Color.BLACK, true), "P",
            Pawn.of(Color.BLACK, false), "P",
            Knight.from(Color.BLACK), "N",
            Bishop.from(Color.BLACK), "B",
            Rook.from(Color.BLACK), "R",
            Queen.from(Color.BLACK), "Q",
            King.from(Color.BLACK), "K"
    );
    private static final Map<Square, String> WHITE_SQUARE_VIEWS = Map.of(
            Pawn.of(Color.WHITE, true), "p",
            Pawn.of(Color.WHITE, false), "p",
            Knight.from(Color.WHITE), "n",
            Bishop.from(Color.WHITE), "b",
            Rook.from(Color.WHITE), "r",
            Queen.from(Color.WHITE), "q",
            King.from(Color.WHITE), "k"
    );
    private static final Map<Square, String> squareViews = new HashMap<>();

    static {
        squareViews.putAll(BLACK_SQUARE_VIEWS);
        squareViews.putAll(WHITE_SQUARE_VIEWS);
        squareViews.put(Empty.getInstance(), ".");
    }


    private OutputView() {
    }

    public static OutputView getInstance() {
        return INSTANCE;
    }

    public void printChessBoard(Map<Position, Square> squares) {
        for (Rank rank : Rank.values()) {
            printRank(squares, rank);
            System.out.println();
        }
        System.out.println();
    }

    private void printRank(Map<Position, Square> squares, Rank rank) {
        for (File file : File.values()) {
            Square square = squares.get(new Position(rank, file));
            System.out.print(squareViews.get(square));
        }
    }
}
