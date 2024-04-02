package chess.view;

import chess.command.CommandType;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class InputView {

    private static final InputView INSTANCE = new InputView(new Scanner(System.in));
    private static final Map<String, CommandType> COMMANDS = Map.of(
            "start", CommandType.START,
            "load", CommandType.LOAD,
            "end", CommandType.END,
            "move", CommandType.MOVE,
            "status", CommandType.STATUS
    );
    private static final String COMMAND_DELIMITER = " ";
    private static final int COMMAND_TYPE_INDEX = 0;

    private final Scanner scanner;

    private InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public static InputView getInstance() {
        return INSTANCE;
    }

    public CommandType readCommand() {
        String command = scanner.next();
        validateExistCommand(command);
        return COMMANDS.get(command);
    }


    private void validateExistCommand(String commandText) {
        if (!COMMANDS.containsKey(commandText)) {
            throw new IllegalArgumentException("존재하지 않는 커맨드입니다.");
        }
    }

    public List<String> readArguments(int count) {
        return Stream.generate(scanner::next)
                .limit(count)
                .toList();
    }
}
