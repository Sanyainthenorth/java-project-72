package hexlet.code.repository;

import hexlet.code.model.Url;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrlRepository extends BaseRepository {

    public static void save(Url url) throws SQLException {
        String sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, url.getName());
            stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            stmt.executeUpdate();

            try (var generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    url.setId(generatedKeys.getLong(1));
                }
            }
        }
    }

    public static Optional<Url> find(Long id) throws SQLException {
        String sql = "SELECT * FROM urls WHERE id = ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(extractUrl(rs));
                }
                return Optional.empty();
            }
        }
    }

    public static Optional<Url> findByName(String name) throws SQLException {
        String sql = "SELECT * FROM urls WHERE name = ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(extractUrl(rs));
                }
                return Optional.empty();
            }
        }
    }

    public static List<Url> getEntities() throws SQLException {
        String sql = "SELECT * FROM urls ORDER BY created_at DESC";
        List<Url> result = new ArrayList<>();
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql);
             var rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.add(extractUrl(rs));
            }
        }
        return result;
    }

    public static void removeAll() throws SQLException {
        try (var conn = dataSource.getConnection();
             var stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM urls");
        }

        // Сброс sequence для H2
        try (var conn = dataSource.getConnection();
             var stmt = conn.createStatement()) {
            stmt.execute("ALTER TABLE urls ALTER COLUMN id RESTART WITH 1");
        } catch (SQLException e) {
            // Игнорируем, если sequence не существует (например, в PostgreSQL)
        }
    }

    private static Url extractUrl(ResultSet rs) throws SQLException {
        Url url = new Url(rs.getString("name"));
        url.setId(rs.getLong("id"));
        url.setCreatedAt(rs.getTimestamp("created_at"));
        return url;
    }
}
