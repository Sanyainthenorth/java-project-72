package hexlet.code.repository;

import hexlet.code.model.Url;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrlRepository extends BaseRepository {

    public static void save(Url url) throws SQLException {
        var sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, url.getName());
            stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            stmt.executeUpdate();

            // Получаем сгенерированный ID
            var generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                url.setId(generatedKeys.getLong(1));
            }
        }
    }

    public static Optional<Url> find(Long id) throws SQLException {
        var sql = "SELECT * FROM urls WHERE id = ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                var url = new Url(rs.getString("name"));
                url.setId(rs.getLong("id"));
                url.setCreatedAt(rs.getTimestamp("created_at"));
                return Optional.of(url);
            }
            return Optional.empty();
        }
    }

    public static Optional<Url> findByName(String name) throws SQLException {
        String sql = "SELECT * FROM urls WHERE name = ?";

        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            var resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                var url = new Url(resultSet.getString("name"));
                url.setId(resultSet.getLong("id"));
                url.setCreatedAt(resultSet.getTimestamp("created_at"));
                return Optional.of(url);
            }

            return Optional.empty();
        }
    }

    public static List<Url> getEntities() throws SQLException {
        var sql = "SELECT * FROM urls ORDER BY created_at DESC";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            var result = new ArrayList<Url>();
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var url = new Url(rs.getString("name"));
                url.setId(rs.getLong("id"));
                url.setCreatedAt(rs.getTimestamp("created_at"));
                result.add(url);
            }
            return result;
        }
    }

    public static void removeAll() throws SQLException {
        var sql = "DELETE FROM urls";
        try (var conn = dataSource.getConnection();
             var stmt = conn.createStatement()) {
            stmt.execute(sql);
        }

        // Сброс sequence для H2
        try (var conn = dataSource.getConnection();
             var stmt = conn.createStatement()) {
            stmt.execute("ALTER TABLE urls ALTER COLUMN id RESTART WITH 1");
        } catch (SQLException e) {
            // Игнорируем, если sequence не существует
        }
    }
}