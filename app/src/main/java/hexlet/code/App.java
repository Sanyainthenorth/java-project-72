package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.model.Url;
import hexlet.code.repository.BaseRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class App {

    public static Javalin getApp() throws IOException, SQLException {
        // Настройка подключения к БД
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(getJdbcUrl());
        var dataSource = new HikariDataSource(hikariConfig);
        BaseRepository.dataSource = dataSource;

        // Создание таблицы
        try (var conn = dataSource.getConnection();
             var stmt = conn.createStatement()) {
            stmt.execute(getSchema());
        }

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
        });

        app.before(ctx -> ctx.contentType("text/html; charset=utf-8"));

        // Маршруты
        app.get("/", ctx -> ctx.result("Hello World"));

        app.get("/urls", ctx -> {
            var urls = UrlRepository.getEntities();
            ctx.json(urls);
        });

        app.post("/urls", ctx -> {
            var url = new Url(ctx.formParam("url"));
            UrlRepository.save(url);
            ctx.redirect("/urls");
        });

        return app;
    }

    private static String getJdbcUrl() {
        return System.getenv().getOrDefault("JDBC_DATABASE_URL", "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");
    }

    private static String getSchema() throws IOException {
        try (var inputStream = App.class.getClassLoader().getResourceAsStream("schema.sql")) {
            if (inputStream == null) {
                throw new IOException("schema.sql not found");
            }
            return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
        }
    }

    public static void main(String[] args) throws IOException, SQLException {
        var app = getApp();
        var port = Integer.parseInt(System.getenv().getOrDefault("PORT", "7070"));
        app.start(port);
    }
}
