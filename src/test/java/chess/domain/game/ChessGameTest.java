package chess.domain.game;

import chess.domain.piece.Color;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import chess.domain.position.TerminalPosition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ChessGameTest {

    @DisplayName("king이 잡히지 않으면 게임은 종료되지 않는다.")
    @Test
    void kingNotDeadTest() {
        // given
        ChessGame chessGame = ChessGame.createOnStart();

        // when & then
        assertThat(chessGame.isGameOver()).isFalse();
    }

    @DisplayName("king이 잡히면 게임이 종료된다.")
    @Test
    void kingDeadTest() {
        // given
        ChessGame chessGame = provideChessGameAsWhiteKingDead();

        // when & then
        assertThat(chessGame.isGameOver()).isTrue();
    }

    @DisplayName("white 킹이 잡히면 black의 승리이다.")
    @Test
    void blackWinTest() {
        // given
        ChessGame chessGame = provideChessGameAsWhiteKingDead();

        // when & then
        assertThat(chessGame.winnerColor()).isEqualTo(Color.BLACK);
    }

    @DisplayName("black 킹이 잡히면 white 승리이다.")
    @Test
    void whiteWinTest() {
        // given
        ChessGame chessGame = provideChessGameAsBlackKingDead();

        // when & then
        assertThat(chessGame.winnerColor()).isEqualTo(Color.WHITE);
    }


    static ChessGame provideChessGameAsWhiteKingDead() {
        /*
        RNBQKBNR
        PPPPPPPP
        ........
        ........
        ........
        ........
        pppppppp
        rnbqkbnr
         */
        ChessGame chessGame = ChessGame.createOnStart();

        /*
        RNBQKBNR
        PPPPPPPP
        ........
        ........
        ........
        .....p..
        ppppp.pp
        rnbqkbnr
         */
        chessGame.movePiece(
                new TerminalPosition(new Position(File.F, Rank.SECOND), new Position(File.F, Rank.THIRD)));

        /*
        RNBQKBNR
        PPPP.PPP
        ........
        ....P...
        ........
        .....p..
        ppppp.pp
        rnbqkbnr
         */
        chessGame.movePiece(
                new TerminalPosition(new Position(File.E, Rank.SEVENTH), new Position(File.E, Rank.FIFTH)));

        /*
        RNBQKBNR
        PPPP.PPP
        ........
        ....P...
        ......p.
        .....p..
        ppppp..p
        rnbqkbnr
         */
        chessGame.movePiece(
                new TerminalPosition(new Position(File.G, Rank.SECOND), new Position(File.G, Rank.FOURTH)));

        /*
        RNB.KBNR
        PPPP.PPP
        ........
        ....P...
        ......pQ
        .....p..
        ppppp..p
        rnbqkbnr
         */
        chessGame.movePiece(
                new TerminalPosition(new Position(File.D, Rank.EIGHTH), new Position(File.H, Rank.FOURTH)));

        /*
        RNB.KBNR
        PPPP.PPP
        ........
        ....P...
        ......pQ
        .....p.p
        ppppp...
        rnbqkbnr
         */
        chessGame.movePiece(
                new TerminalPosition(new Position(File.H, Rank.SECOND), new Position(File.H, Rank.THIRD)));

        /*
        RNB.KBNR
        PPPP.PPP
        ........
        ....P...
        ......p.
        .....p.p
        ppppp...
        rnbqQbnr
         */
        chessGame.movePiece(
                new TerminalPosition(new Position(File.H, Rank.FOURTH), new Position(File.E, Rank.FIRST)));

        return chessGame;
    }

    static ChessGame provideChessGameAsBlackKingDead() {
        /*
        RNBQKBNR
        PPPPPPPP
        ........
        ........
        ........
        ........
        pppppppp
        rnbqkbnr
         */
        ChessGame chessGame = ChessGame.createOnStart();

        /*
        RNBQKBNR
        PPPPPPPP
        ........
        ........
        ........
        ....p...
        pppp.ppp
        rnbqkbnr
         */
        chessGame.movePiece(
                new TerminalPosition(new Position(File.E, Rank.SECOND), new Position(File.E, Rank.THIRD)));

        /*
        RNBQKBNR
        PPPPP.PP
        .....P..
        ........
        ........
        ....p...
        pppp.ppp
        rnbqkbnr
         */
        chessGame.movePiece(
                new TerminalPosition(new Position(File.F, Rank.SEVENTH), new Position(File.F, Rank.SIXTH)));

        /*
        RNBQKBNR
        PPPPP.PP
        .....P..
        .......q
        ........
        ....p...
        pppp.ppp
        rnb.kbnr
         */
        chessGame.movePiece(
                new TerminalPosition(new Position(File.D, Rank.FIRST), new Position(File.H, Rank.FIFTH)));

        /*
        RNBQKB.R
        PPPPP.PP
        .....P.N
        .......q
        ........
        ....p...
        pppp.ppp
        rnb.kbnr
         */
        chessGame.movePiece(
                new TerminalPosition(new Position(File.G, Rank.EIGHTH), new Position(File.H, Rank.SIXTH)));

        /*
        RNBQqB.R
        PPPPP.PP
        .....P.N
        ........
        ........
        ....p...
        pppp.ppp
        rnb.kbnr
         */
        chessGame.movePiece(
                new TerminalPosition(new Position(File.H, Rank.FIFTH), new Position(File.E, Rank.EIGHTH)));

        return chessGame;
    }
}
