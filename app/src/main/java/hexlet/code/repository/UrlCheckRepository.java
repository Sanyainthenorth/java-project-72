package hexlet.code.repository;

import hexlet.code.model.UrlCheck;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.sql.ResultSet;

public class UrlCheckRepository extends BaseRepository {

    public static void save(UrlCheck urlCheck) throws SQLException {
        String sql = "INSERT INTO url_checks (url_id, status_code, title, h1, description, created_at) "
            + "VALUES (?, ?, ?, ?, ?, ?)";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, urlCheck.getUrlId());
            stmt.setInt(2, urlCheck.getStatusCode());
            stmt.setString(3, urlCheck.getTitle());
            stmt.setString(4, urlCheck.getH1());
            stmt.setString(5, urlCheck.getDescription());
            stmt.setTimestamp(6, urlCheck.getCreatedAt());
            stmt.executeUpdate();
        }
    }

    public static List<UrlCheck> findAllByUrlId(Long urlId) throws SQLException {
        String sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY created_at DESC";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, urlId);
            var resultSet = stmt.executeQuery();
            var urlChecks = new ArrayList<UrlCheck>();
            while (resultSet.next()) {
                urlChecks.add(extractUrlCheck(resultSet));
            }
            return urlChecks;
        }
    }

    public static Optional<UrlCheck> findLatestCheckByUrlId(Long urlId) throws SQLException {
        String sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY created_at DESC LIMIT 1";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, urlId);
            var resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return Optional.of(extractUrlCheck(resultSet));
            }
            return Optional.empty();
        }
    }

    public static Map<Long, UrlCheck> findLatestChecksForAllUrls() throws SQLException {
        String sql = """
            SELECT DISTINCT ON (url_id) *
            FROM url_checks
            ORDER BY url_id, created_at DESC
            """;

        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {

            var resultSet = stmt.executeQuery();
            var latestChecks = new HashMap<Long, UrlCheck>();

            while (resultSet.next()) {
                UrlCheck urlCheck = extractUrlCheck(resultSet);
                latestChecks.put(urlCheck.getUrlId(), urlCheck);
            }

            return latestChecks;
        }
    }

    public static void removeAll() throws SQLException {
        String sql = "DELETE FROM url_checks";
        try (var conn = dataSource.getConnection();
             var stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    private static UrlCheck extractUrlCheck(ResultSet resultSet) throws SQLException {
        var urlCheck = new UrlCheck();
        urlCheck.setId(resultSet.getLong("id"));
        urlCheck.setUrlId(resultSet.getLong("url_id"));
        urlCheck.setStatusCode(resultSet.getInt("status_code"));
        urlCheck.setTitle(resultSet.getString("title"));
        urlCheck.setH1(resultSet.getString("h1"));
        urlCheck.setDescription(resultSet.getString("description"));
        urlCheck.setCreatedAt(resultSet.getTimestamp("created_at"));
        return urlCheck;
    }
}

