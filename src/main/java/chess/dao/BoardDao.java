package chess.dao;

import chess.domain.piece.Empty;
import chess.domain.piece.Piece;
import chess.domain.position.Position;

import java.sql.Connection;
import java.sql.SQLException;
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
}
