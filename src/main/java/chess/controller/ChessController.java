package chess.controller;

import chess.command.CommandType;
import chess.domain.game.ChessGame;
import chess.domain.position.StartEndPosition;
import chess.util.ExceptionRetryHandler;
import chess.view.InputView;
import chess.view.OutputView;
import chess.view.StartEndPositionView;

import java.util.Map;
import java.util.function.Consumer;

public class ChessController {
    private static final int MOVE_ARGUMENTS_COUNT = 2;

    private final InputView inputView;
    private final OutputView outputView;

    public ChessController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        outputView.printStartMessage();
        ChessGame chessGame = ChessGame.create();
        processGameUntilValid(chessGame);
    }

    private void processGameUntilValid(ChessGame chessGame) {
        ExceptionRetryHandler.handle(() -> processGame(chessGame));
    }

    private void processGame(ChessGame chessGame) {
        Map<CommandType, Consumer<ChessGame>> commandInvoker = createCommandInvoker();
        CommandType commandType = inputView.readCommand();
        commandInvoker.get(commandType).accept(chessGame);
    }

    private Map<CommandType, Consumer<ChessGame>> createCommandInvoker() {
        return Map.of(
                CommandType.START, this::startGame,
                CommandType.MOVE, this::move,
                CommandType.STATUS, this::status,
                CommandType.END, this::endGame
        );
    }

    private void startGame(ChessGame chessGame) {
        chessGame.startGame();
        outputView.printChessBoard(chessGame.getPieces());

        while (!chessGame.isNotProcess()) {
            processGameUntilValid(chessGame);
        }
        printGameResult(chessGame);
    }

    private void printGameResult(ChessGame chessGame) {
        if (chessGame.isKingDead()) {
            outputView.printGameResult(chessGame.winnerColor());
        }
    }

    private void move(ChessGame chessGame) {
        StartEndPosition startEndPosition = StartEndPositionView.of(inputView.readArguments(MOVE_ARGUMENTS_COUNT));
        chessGame.movePiece(startEndPosition);
        outputView.printChessBoard(chessGame.getPieces());
    }

    private void status(ChessGame chessGame) {
        outputView.printStatus(chessGame.status());
    }

    private void endGame(ChessGame chessGame) {
        chessGame.endGame();
    }
}
