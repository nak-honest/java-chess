import chess.controller.ChessController;
import chess.service.GameService;
import chess.view.InputView;
import chess.view.OutputView;

public class Application {
    public static void main(String[] args) {
        ChessController controller = new ChessController(
                new GameService(),
                InputView.getInstance(),
                OutputView.getInstance());
        controller.run();
    }
}
