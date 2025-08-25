package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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
    void testAddUrlSuccess() throws IOException {
        JavalinTest.test(app, (server, client) -> {
            // Получаем URL с работающим сервером
            String baseUrl = "http://localhost:" + server.port();

            // Создаем OkHttpClient с отключенным следованием редиректам
            OkHttpClient httpClient = new OkHttpClient.Builder()
                .followRedirects(false)
                .build();

            // Формируем тело запроса form-urlencoded
            MediaType mediaType = MediaType.get("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create("url=https://example.com", mediaType);

            // Формируем POST запрос
            Request request = new Request.Builder()
                .url(baseUrl + "/urls")
                .post(body)
                .build();

            // Выполняем запрос и получаем ответ
            Response response = httpClient.newCall(request).execute();

            // Проверяем, что ответ именно 302 (редирект)
            assertThat(response.code()).isEqualTo(302);

            // Проверяем что URL добавлен в базу
            List<Url> urls = UrlRepository.getEntities();
            assertThat(urls).hasSize(1);
            assertThat(urls.get(0).getName()).isEqualTo("https://example.com");

            // Проверяем, что GET /urls возвращает 200 и содержит добавленный URL
            var listResponse = client.get("/urls");
            assertThat(listResponse.code()).isEqualTo(200);
            assertThat(listResponse.body().string()).contains("https://example.com");
        });
    }



    @Test
    public void testAddUrlDuplicate() throws IOException {
        JavalinTest.test(app, (server, client) -> {
            UrlRepository.save(new Url("https://example.com"));

            String baseUrl = "http://localhost:" + server.port();

            OkHttpClient httpClient = new OkHttpClient.Builder()
                .followRedirects(false)
                .build();

            MediaType mediaType = MediaType.get("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create("url=https://example.com", mediaType);

            Request request = new Request.Builder()
                .url(baseUrl + "/urls")
                .post(body)
                .build();

            Response response = httpClient.newCall(request).execute();
            assertThat(response.code()).isEqualTo(302);

            List<Url> urls = UrlRepository.getEntities();
            assertThat(urls).hasSize(1);
        });
    }

    @Test
    public void testAddUrlInvalid() throws IOException {
        JavalinTest.test(app, (server, client) -> {

            String baseUrl = "http://localhost:" + server.port();

            OkHttpClient httpClient = new OkHttpClient.Builder()
                .followRedirects(false)
                .build();

            MediaType mediaType = MediaType.get("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create("url=badinvalidurl", mediaType);

            Request request = new Request.Builder()
                .url(baseUrl + "/urls")
                .post(body)
                .build();

            Response response = httpClient.newCall(request).execute();
            assertThat(response.code()).isEqualTo(302);

            List<Url> urls = UrlRepository.getEntities();
            assertThat(urls).isEmpty();
        });
    }

    @Test
    public void testGetUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            UrlRepository.save(new Url("https://example.com"));
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://example.com");
        });
    }

    @Test
    public void testGetUrlDetails() {
        JavalinTest.test(app, (server, client) -> {
            Url url = new Url("https://example.com");
            UrlRepository.save(url);
            var response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://example.com");
        });
    }

    @Test
    public void testUrlNotFound() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/999999");
            assertThat(response.code()).isEqualTo(404);
        });
    }
}
