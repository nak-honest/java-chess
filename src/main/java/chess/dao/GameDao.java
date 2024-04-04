package chess.dao;

import chess.domain.piece.Color;
import chess.entity.GameEntity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.OptionalInt;

public class GameDao {
    private final Connection connection;

    public GameDao(Connection connection) {
        this.connection = connection;
    }

    public OptionalInt save(Color currentTurn) {
        final var query = "INSERT INTO game (game_turn) VALUES (?);";
        try (final var statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, currentTurn.name());
            statement.executeUpdate();
            final ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                return OptionalInt.of(generatedKeys.getInt(1));
            }
            return OptionalInt.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<GameEntity> findRecentGame() {
        final var query = "SELECT id, game_turn FROM game ORDER BY saved_at DESC LIMIT 1;";
        try (final var statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                Color gameTurn = Color.valueOf(resultSet.getString("game_turn"));
                return Optional.of(new GameEntity(id, gameTurn));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll() {
        final var query = "DELETE FROM game;";
        try (final var statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
