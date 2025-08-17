package hexlet.code.repository;

import hexlet.code.model.Url;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UrlRepository {
    public static void save(Url url) throws SQLException {
        var sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        try (var conn = BaseRepository.dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, url.getName());
            stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            stmt.executeUpdate();
        }
    }

    public static List<Url> getEntities() throws SQLException {
        var sql = "SELECT * FROM urls";
        try (var conn = BaseRepository.dataSource.getConnection();
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
}