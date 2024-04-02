package chess.dao;

import chess.domain.piece.Color;
import chess.domain.piece.Empty;
import chess.domain.piece.Piece;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BoardDao {
    private final Connection connection;

    public BoardDao(Connection connection) {
        this.connection = connection;
    }

    public void save(int gameId, Map<Position, Piece> board) {
        final var query = "INSERT INTO board (game_id, rank_position, file_position, piece_color, piece_type) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (final var statement = connection.prepareStatement(query)) {
            for (Position position : keySetWithoutEmpty(board)) {
                Piece piece = board.get(position);
                statement.setInt(1, gameId);
                statement.setInt(2, position.getRank().getValue());
                statement.setInt(3, position.getFile().getValue());
                statement.setString(4, piece.getColor().name());
                statement.setString(5, PieceTypeMapper.from(piece).name());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<Position> keySetWithoutEmpty(Map<Position, Piece> board) {
        return board.keySet().stream()
                .filter(position -> board.get(position) != Empty.getInstance())
                .collect(Collectors.toSet());
    }

    public Map<Position, Piece> findByGameId(int gameId) {
        final var query = "SELECT * FROM board WHERE game_id = ?";
        try (final var statement = connection.prepareStatement(query)) {
            statement.setInt(1, gameId);
            Map<Position, Piece> board = createEmptyBoard();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Rank rank = Rank.from(resultSet.getInt("rank_position"));
                File file = File.from(resultSet.getInt("file_position"));
                Color color = Color.valueOf(resultSet.getString("piece_color"));
                Piece piece = PieceTypeMapper.findPiece(resultSet.getString("piece_type"), color);

                board.put(new Position(file, rank), piece);
            }
            return board;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<Position, Piece> createEmptyBoard() {
        Map<Position, Piece> pieces = new HashMap<>();

        for (Rank rank : Rank.values()) {
            pieces.putAll(createEmptyFileByRank(rank));
        }

        return pieces;
    }

    private Map<Position, Piece> createEmptyFileByRank(Rank rank) {
        Map<Position, Piece> pieces = new HashMap<>();
        for (File file : File.values()) {
            pieces.put(new Position(file, rank), Empty.getInstance());
        }

        return pieces;
    }

    public void deleteAll() {
        final var query = "DELETE FROM board;";
        try (final var statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
