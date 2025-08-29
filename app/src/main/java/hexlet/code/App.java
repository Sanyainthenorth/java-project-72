package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import hexlet.code.dto.MainPage;
import hexlet.code.dto.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.BaseRepository;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import io.javalin.rendering.template.JavalinJte;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.net.URL;
import kong.unirest.Unirest;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestException;
import java.sql.Timestamp;

import static io.javalin.rendering.template.TemplateUtil.model;

public class App {

    private static int getPort() {
        return Integer.parseInt(System.getenv().getOrDefault("PORT", "7070"));
    }

    private static String getJdbcUrl() {
        String jdbcUrl = System.getenv().getOrDefault("JDBC_DATABASE_URL", "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");
        return jdbcUrl;
    }

    private static String readResourceFile(String fileName) throws IOException {
        var inputStream = App.class.getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IOException("Resource file not found: " + fileName);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

    public static void main(String[] args) throws IOException, SQLException {
        var app = getApp();
        app.start(getPort());
    }

    public static Javalin getApp() throws IOException, SQLException {
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(getJdbcUrl());
        var dataSource = new HikariDataSource(hikariConfig);
        var sql = readResourceFile("schema.sql");

        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }
        BaseRepository.dataSource = dataSource;

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        app.before(ctx -> {
            ctx.contentType("text/html; charset=utf-8");
        });

        app.get(
            "/", ctx -> {
                try {
                    var page = new MainPage();
                    page.setFlash((String) ctx.sessionAttribute("flash"));
                    page.setFlashType((String) ctx.sessionAttribute("flashType"));

                    ctx.consumeSessionAttribute("flash");
                    ctx.consumeSessionAttribute("flashType");

                    ctx.render("index.jte", model("page", page));
                }
                catch (Exception e) {
                    ctx.status(500).result("Error: " + e.getMessage());
                    throw e;
                }
            }
        );

        // Обработка добавления URL
        app.post(
            "/urls", ctx -> {
                String inputUrl = ctx.formParam("url");
                if (inputUrl == null || inputUrl.trim().isEmpty()) {
                    ctx.sessionAttribute("flash", "URL не может быть пустым");
                    ctx.sessionAttribute("flashType", "danger");
                    ctx.redirect("/");
                    return;
                }

                String normalizedUrl;
                try {
                    var uri = new URI(inputUrl);
                    String protocol = uri.getScheme();
                    String host = uri.getHost();

                    if (protocol == null || host == null) {
                        throw new IllegalArgumentException("Некорректный URL");
                    }

                    int port = uri.getPort();
                    if (port == -1 || (protocol.equals("http") && port == 80) ||
                        (protocol.equals("https") && port == 443)) {
                        normalizedUrl = protocol + "://" + host;
                    } else {
                        normalizedUrl = protocol + "://" + host + ":" + port;
                    }
                }
                catch (Exception ex) {
                    ctx.sessionAttribute("flash", "Некорректный URL");
                    ctx.sessionAttribute("flashType", "danger");
                    ctx.redirect("/");
                    return;
                }

                try {
                    var existingUrl = UrlRepository.findByName(normalizedUrl);
                    if (existingUrl.isPresent()) {
                        ctx.sessionAttribute("flash", "Страница уже существует");
                        ctx.sessionAttribute("flashType", "info");
                    } else {
                        UrlRepository.save(new Url(normalizedUrl));
                        ctx.sessionAttribute("flash", "Страница успешно добавлена");
                        ctx.sessionAttribute("flashType", "success");
                    }
                    ctx.redirect("/urls");
                }
                catch (SQLException e) {
                    ctx.sessionAttribute("flash", "Ошибка при работе с базой данных");
                    ctx.sessionAttribute("flashType", "danger");
                    ctx.redirect("/");
                }
            }
        );

        // Список всех URL
        app.get(
            "/urls", ctx -> {
                try {
                    var urls = UrlRepository.getEntities();
                    var page = new UrlsPage(urls);

                    // Загружаем последние проверки для каждого URL
                    Map<Long, UrlCheck> latestChecks = new HashMap<>();
                    for (Url url : urls) {
                        Optional<UrlCheck> check = UrlCheckRepository.findLatestCheckByUrlId(url.getId());
                        check.ifPresent(urlCheck -> latestChecks.put(url.getId(), urlCheck));
                    }
                    page.setLatestChecks(latestChecks);

                    page.setFlash((String) ctx.sessionAttribute("flash"));
                    page.setFlashType((String) ctx.sessionAttribute("flashType"));

                    ctx.consumeSessionAttribute("flash");
                    ctx.consumeSessionAttribute("flashType");

                    ctx.render("urls/index.jte", model("page", page));
                }
                catch (SQLException e) {
                    ctx.sessionAttribute("flash", "Ошибка при получении списка URL");
                    ctx.sessionAttribute("flashType", "danger");
                    ctx.redirect("/");
                }
            }
        );

        // Конкретный URL
        app.get(
            "/urls/{id}", ctx -> {
                try {
                    long id = Long.parseLong(ctx.pathParam("id"));
                    Optional<Url> urlOptional = UrlRepository.find(id);

                    if (urlOptional.isEmpty()) {
                        throw new NotFoundResponse("URL не найден");
                    }

                    Url url = urlOptional.get();
                    List<UrlCheck> checks = UrlCheckRepository.findAllByUrlId(id);

                    ctx.attribute("url", url);
                    ctx.attribute("checks", checks);
                    ctx.render("urls/show.jte");
                }
                catch (SQLException e) {
                    ctx.sessionAttribute("flash", "Ошибка базы данных: " + e.getMessage());
                    ctx.sessionAttribute("flashType", "danger");
                    ctx.redirect("/urls");
                }
            }
        );
        app.post(
            "/urls/{id}/checks", ctx -> {
                try {
                    long id = Long.parseLong(ctx.pathParam("id"));
                    Optional<Url> urlOptional = UrlRepository.find(id);

                    if (urlOptional.isEmpty()) {
                        throw new NotFoundResponse("URL не найден");
                    }

                    Url url = urlOptional.get();

                    try {
                        HttpResponse<String> response = Unirest.get(url.getName())
                                                               .connectTimeout(5000)
                                                               .asString();

                        String body = response.getBody();
                        String title = extractTagContent(body, "title");
                        String h1 = extractTagContent(body, "h1");
                        String description = extractMetaDescription(body);

                        UrlCheck urlCheck = new UrlCheck();
                        urlCheck.setUrlId(id);
                        urlCheck.setStatusCode(response.getStatus());
                        urlCheck.setTitle(title);
                        urlCheck.setH1(h1);
                        urlCheck.setDescription(description);
                        urlCheck.setCreatedAt(new Timestamp(System.currentTimeMillis()));

                        // Сохраняем UrlCheck только если HTTP статус 200
                        if (response.getStatus() == 200) {
                            UrlCheckRepository.save(urlCheck);
                            ctx.sessionAttribute("flash", "Страница успешно проверена");
                            ctx.sessionAttribute("flashType", "success");
                        } else {
                            ctx.sessionAttribute("flash", "Не удалось проверить страницу: получен статус " + response.getStatus());
                            ctx.sessionAttribute("flashType", "danger");
                        }
                    }
                    catch (UnirestException e) {
                        ctx.sessionAttribute("flash", "Невозможно проверить страницу: " + e.getMessage());
                        ctx.sessionAttribute("flashType", "danger");
                    }

                    ctx.redirect("/urls/" + id);
                }
                catch (SQLException e) {
                    ctx.sessionAttribute("flash", "Ошибка базы данных: " + e.getMessage());
                    ctx.sessionAttribute("flashType", "danger");
                    ctx.redirect("/urls");
                }
            }
        );


        return app;
    }

    // Вспомогательные методы для простого парсинга html без Jsoup
    private static String extractTagContent(String html, String tag) {
        Pattern pattern = Pattern.compile("<" + tag + ".*?>(.*?)</" + tag + ">", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return "";
    }

    private static String extractMetaDescription(String html) {
        Pattern pattern = Pattern.compile(
            "<meta\\s+name=['\"]description['\"]\\s+content=['\"](.*?)['\"][^>]*>",
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return "";
    }

}
