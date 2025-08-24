package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    private Javalin app;

    @BeforeEach
    public void setUp() throws IOException, SQLException {
        // Используем H2 для тестов
        //System.setProperty("JDBC_DATABASE_URL", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;");

        app = App.getApp();
        UrlRepository.removeAll();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Добавить новый URL");
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Список URL");
        });
    }

    @Test
    public void testAddValidUrl() throws SQLException {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://example.com";

            // Создаем кастомный HTTP клиент без редиректов
            OkHttpClient noRedirectClient = new OkHttpClient.Builder()
                .followRedirects(false)
                .build();

            var requestBodyObj = RequestBody.create(
                "url=https://example.com",
                MediaType.parse("application/x-www-form-urlencoded")
            );

            Request request = new Request.Builder()
                .url("http://localhost:" + server.port() + "/urls")
                .post(requestBodyObj)
                .build();

            var response = noRedirectClient.newCall(request).execute();

            // Проверяем редирект
            assertThat(response.code()).isEqualTo(302);
            assertThat(response.header("Location")).isEqualTo("/urls");

            var urls = UrlRepository.getEntities();
            assertThat(urls).hasSize(1);
            assertThat(urls.get(0).getName()).isEqualTo("https://example.com");
        });
    }

    @Test
    public void testAddInvalidUrl() {
        JavalinTest.test(app, (server, client) -> {
            // Создаем кастомный HTTP клиент без редиректов
            OkHttpClient noRedirectClient = new OkHttpClient.Builder()
                .followRedirects(false)
                .build();

            var requestBody = RequestBody.create(
                "url=invalid-url",
                MediaType.parse("application/x-www-form-urlencoded")
            );

            Request request = new Request.Builder()
                .url("http://localhost:" + server.port() + "/urls")
                .post(requestBody)
                .build();

            var response = noRedirectClient.newCall(request).execute();

            // Проверяем, что возвращается редирект (302)
            assertThat(response.code()).isEqualTo(302);
            assertThat(response.header("Location")).isEqualTo("/");

            var urls = UrlRepository.getEntities();
            assertThat(urls).isEmpty();
        });
    }

    @Test
    void testAddDuplicateUrlRedirect() {
        JavalinTest.test(app, (server, client) -> {
            // Создаем кастомный HTTP клиент без редиректов
            OkHttpClient noRedirectClient = new OkHttpClient.Builder()
                .followRedirects(false)
                .build();

            // Первый запрос
            Request request1 = new Request.Builder()
                .url("http://localhost:" + server.port() + "/urls")
                .post(RequestBody.create("url=https://example.com",
                                         MediaType.parse("application/x-www-form-urlencoded")))
                .build();
            var response1 = noRedirectClient.newCall(request1).execute();
            assertEquals(302, response1.code());

            // Второй запрос (дубликат)
            Request request2 = new Request.Builder()
                .url("http://localhost:" + server.port() + "/urls")
                .post(RequestBody.create("url=https://example.com",
                                         MediaType.parse("application/x-www-form-urlencoded")))
                .build();
            var response2 = noRedirectClient.newCall(request2).execute();

            // Проверяем статус второго POST запроса
            assertEquals(302, response2.code());
        });
    }

    @Test
    public void testShowUrlPage() throws SQLException {
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
            // Создаем кастомный HTTP клиент без редиректов
            OkHttpClient noRedirectClient = new OkHttpClient.Builder()
                .followRedirects(false)
                .build();

            var requestBody = RequestBody.create(
                "url=https://example.com:443/path?query=string",
                MediaType.parse("application/x-www-form-urlencoded")
            );

            Request request = new Request.Builder()
                .url("http://localhost:" + server.port() + "/urls")
                .post(requestBody)
                .build();

            var response = noRedirectClient.newCall(request).execute();

            // Проверяем редирект
            assertThat(response.code()).isEqualTo(302);

            var urls = UrlRepository.getEntities();
            assertThat(urls).hasSize(1);
            assertThat(urls.get(0).getName()).isEqualTo("https://example.com");
        });
    }

}
