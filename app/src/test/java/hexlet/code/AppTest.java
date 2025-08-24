package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {

    private Javalin app;

    @BeforeEach
    public void setUp() throws IOException, SQLException {
        // Используем H2 для тестов
        System.setProperty("JDBC_DATABASE_URL", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;");

        app = App.getApp();
        // Очищаем базу данных перед каждым тестом
        UrlRepository.removeAll();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Анализатор страниц");
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Сайты");
        });
    }

    @Test
    public void testAddValidUrl() throws SQLException {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://example.com";
            var response = client.post("/urls", requestBody);

            assertThat(response.code()).isEqualTo(302); // Redirect
            assertThat(response.header("Location")).isEqualTo("/urls");

            // Проверяем, что URL был добавлен в базу
            var urls = UrlRepository.getEntities();
            assertThat(urls).hasSize(1);
            assertThat(urls.get(0).getName()).isEqualTo("https://example.com");
        });
    }

    @Test
    public void testAddInvalidUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=invalid-url";
            var response = client.post("/urls", requestBody);

            assertThat(response.code()).isEqualTo(302);
            assertThat(response.header("Location")).isEqualTo("/");

            // Проверяем, что URL не был добавлен в базу
            var urls = UrlRepository.getEntities();
            assertThat(urls).isEmpty();
        });
    }

    @Test
    public void testAddDuplicateUrl() throws SQLException {
        // Сначала добавляем URL
        var url = new Url("https://example.com");
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://example.com";
            var response = client.post("/urls", requestBody);

            assertThat(response.code()).isEqualTo(302);
            assertThat(response.header("Location")).isEqualTo("/urls");

            // Проверяем, что URL не был добавлен повторно
            var urls = UrlRepository.getEntities();
            assertThat(urls).hasSize(1);
        });
    }

    @Test
    public void testShowUrlPage() throws SQLException {
        // Сначала добавляем URL
        var url = new Url("https://example.com");
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://example.com");
        });
    }

    @Test
    public void testShowNonExistentUrl() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/999999");
            assertThat(response.code()).isEqualTo(404);
        });
    }

    @Test
    public void testUrlNormalization() throws SQLException {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://example.com:443/path?query=string";
            var response = client.post("/urls", requestBody);

            assertThat(response.code()).isEqualTo(302);

            // Проверяем, что URL был нормализован
            var urls = UrlRepository.getEntities();
            assertThat(urls).hasSize(1);
            assertThat(urls.get(0).getName()).isEqualTo("https://example.com");
        });
    }
}