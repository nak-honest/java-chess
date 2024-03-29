package chess.controller;

import chess.command.Command;
import chess.command.CommandType;
import chess.domain.game.ChessGame;
import chess.domain.position.TerminalPosition;
import chess.util.ExceptionRetryHandler;
import chess.view.InputView;
import chess.view.OutputView;
import chess.view.TerminalPositionView;

public class ChessController {
    private final InputView inputView;
    private final OutputView outputView;

    public ChessController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        outputView.printStartMessage();
        Command command = receiveStartCommandUntilValid();
        validateStartOrEnd(command);

        if (command.isStart()) {
            startGame();
        }
    }

    private Command receiveStartCommandUntilValid() {
        return ExceptionRetryHandler.handle(inputView::readCommand);
    }

    private static void validateStartOrEnd(Command command) {
        if (command.isNotStartOrEnd()) {
            throw new IllegalArgumentException("게임을 시작하거나 끝내는 것만 가능합니다.");
        }
    }

    private void startGame() {
        ChessGame chessGame = ChessGame.createOnStart();
        outputView.printChessBoard(chessGame.getPieces());

        processGameUntilValid(chessGame);
        printGameResult(chessGame);
    }

    private void printGameResult(ChessGame chessGame) {
        if (chessGame.isGameOver()) {
            outputView.printGameResult(chessGame.winnerColor());
        }
    }

    private void processGameUntilValid(ChessGame chessGame) {
        ExceptionRetryHandler.handle(() -> processGame(chessGame));
    }

    private void processGame(ChessGame chessGame) {
        Command command = receiveProcessCommand(chessGame);

        while (command.isNotEnd()) {
            processTurn(command, chessGame);
            command = receiveProcessCommand(chessGame);
        }
    }

    private Command receiveProcessCommand(ChessGame chessGame) {
        // TODO: 상태 관리가 어려우니 객체로 바라볼지 고려해 보기
        if (chessGame.isGameOver()) {
            return Command.createNoArgCommand(CommandType.END);
        }

        Command command = inputView.readCommand();
        validateNotStart(command);
        return command;
    }

    private void validateNotStart(Command command) {
        if (command.isStart()) {
            throw new IllegalArgumentException("게임이 이미 진행중입니다.");
        }
    }

    private void processTurn(Command command, ChessGame chessGame) {
        tryMove(command, chessGame);
        tryStatus(command, chessGame);
    }

    private void tryMove(Command command, ChessGame chessGame) {
        if (command.isMove()) {
            TerminalPosition terminalPosition = TerminalPositionView.of(command.getArguments());
            chessGame.movePiece(terminalPosition);
            outputView.printChessBoard(chessGame.getPieces());
        }
    }

    private void tryStatus(Command command, ChessGame chessGame) {
        if (command.isStatus()) {
            outputView.printStatus(chessGame.status());
        }
    }
}
