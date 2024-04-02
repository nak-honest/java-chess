package chess.dao;

import chess.domain.game.state.State;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class GameDao {
    private final Connection connection;

    public GameDao(Connection connection) {
        this.connection = connection;
    }

    public Optional<Integer> save(State state) {
        final var query = "INSERT INTO game (game_turn) VALUES (?);";
        try (final var statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, TurnMapper.from(state).name());
            statement.executeUpdate();
            final ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                return Optional.of(generatedKeys.getInt(1));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<State> findRecent() {
        final var query = "SELECT game_turn FROM game ORDER BY saved_at DESC LIMIT 1;";
        try (final var statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                State state = TurnMapper.findState(resultSet.getString("game_turn"));
                return Optional.of(state);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
