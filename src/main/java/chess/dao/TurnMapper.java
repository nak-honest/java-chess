package chess.dao;

import chess.domain.game.Turn;
import chess.domain.game.state.ProcessState;
import chess.domain.game.state.State;
import chess.domain.piece.Color;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public enum TurnMapper {
    WHITE_TURN(
            state -> state.equals(ProcessState.from(Turn.from(List.of(Color.WHITE, Color.BLACK)))),
            () -> ProcessState.from(Turn.from(List.of(Color.WHITE, Color.BLACK)))),
    BLACK_TURN(
            state -> state.equals(ProcessState.from(Turn.from(List.of(Color.BLACK, Color.WHITE)))),
            () -> ProcessState.from(Turn.from(List.of(Color.BLACK, Color.WHITE))));

    private final Predicate<State> matcher;
    private final Supplier<State> stateCreator;

    TurnMapper(Predicate<State> matcher, Supplier<State> stateCreator) {
        this.matcher = matcher;
        this.stateCreator = stateCreator;
    }

    public static TurnMapper from(State state) {
        return Arrays.stream(values())
                .filter(turnMapper -> turnMapper.matcher.test(state))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("진행중인 게임만 턴을 가지고 있습니다."));
    }

    public static State findState(String name) {
        return Arrays.stream(values())
                .filter(turnMapper -> name.equals(turnMapper.name()))
                .map(turnMapper -> turnMapper.stateCreator.get())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 State입니다."));
    }
}
