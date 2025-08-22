package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppTest {

    private Javalin app;

    @BeforeEach
    void setUp() throws IOException, SQLException {
        app = App.getApp();

        // Очистим таблицу URL для независимости тестов (нужно реализовать метод)
        UrlRepository.removeAll();
    }

    @Test
    void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Добавить ссылку");  // проверьте содержимое вашей index.jte
        });
    }

    @Test
    void testAddUrlSuccessfully() throws SQLException {
        JavalinTest.test(app, (server, client) -> {
            var form = "url=https://hexlet.io";
            var postResponse = client.post("/urls", form);

            // Проверяем редирект после успешного добавления
            assertThat(postResponse.code()).isEqualTo(302);
            assertThat(postResponse.header("Location")).isEqualTo("/urls");

            // Проверяем, что URL добавлен в базу
            Optional<Url> url = UrlRepository.findByName("https://hexlet.io");
            assertThat(url).isPresent();

            // Проверяем, что URL отображается на странице /urls
            var listResponse = client.get("/urls");
            assertThat(listResponse.code()).isEqualTo(200);
            assertThat(listResponse.body().string()).contains("https://hexlet.io");
        });
    }

    @Test
    void testAddInvalidUrl() {
        JavalinTest.test(app, (server, client) -> {
            var form = "url=not-a-valid-url";
            var postResponse = client.post("/urls", form);

            // Ожидается редирект на главную со flash-сообщением
            assertThat(postResponse.code()).isEqualTo(302);
            assertThat(postResponse.header("Location")).isEqualTo("/");

            // Главная страница с сообщением об ошибке
            var mainResponse = client.get("/");
            var body = mainResponse.body().string();
            assertThat(body).contains("Некорректный URL");
        });
    }

    @Test
    void testUrlsIndexPage() throws SQLException {
        // Добавим URL для проверки
        UrlRepository.save(new Url("https://test-url.com"));

        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://test-url.com");
        });
    }

    @Test
    void testShowUrlPage() throws SQLException {
        var url = new Url("https://show-url.com");
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://show-url.com");
        });
    }

    @Test
    void testShowUrlPageNotFound() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/99999999");
            assertThat(response.code()).isEqualTo(404);
        });
    }
}
