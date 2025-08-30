package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlCheckTest {

    private static Javalin app;
    private static MockWebServer mockWebServer;
    private static OkHttpClient client;

    @BeforeAll
    public static void setUpAll() throws IOException, SQLException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        app = App.getApp();
        app.start(0); // стартуем на случайном порту

        client = new OkHttpClient.Builder()
            .followRedirects(false) // отключаем автоматический редирект
            .build();
    }

    @AfterAll
    public static void tearDownAll() throws IOException {
        app.stop();
        mockWebServer.shutdown();
    }

    @BeforeEach
    public void setUp() throws SQLException {
        UrlRepository.removeAll();
        UrlCheckRepository.removeAll();
    }

    @Test
    public void testUrlCheck() throws IOException, SQLException {
        String mockHtml = """
            <!DOCTYPE html>
            <html>
            <head>
                <title>Test Page</title>
                <meta name="description" content="Test description">
            </head>
            <body>
                <h1>Test Header</h1>
                <p>Test content</p>
            </body>
            </html>
            """;

        mockWebServer.enqueue(new MockResponse().setBody(mockHtml).setResponseCode(200));

        String testUrl = mockWebServer.url("/").toString().replaceAll("/$", "");
        String baseUrl = "http://localhost:" + app.port();

        RequestBody formBody = new FormBody.Builder()
            .add("url", testUrl)
            .build();

        Request postUrls = new Request.Builder()
            .url(baseUrl + "/urls")
            .post(formBody)
            .build();

        try (Response response = client.newCall(postUrls).execute()) {
            assertThat(response.code()).isEqualTo(302);
        }

        List<Url> urls = UrlRepository.getEntities();
        assertThat(urls).isNotEmpty();
        Url url = urls.get(0);

        Request postChecks = new Request.Builder()
            .url(baseUrl + "/urls/" + url.getId() + "/checks")
            .post(RequestBody.create(new byte[0]))
            .build();

        try (Response response = client.newCall(postChecks).execute()) {
            assertThat(response.code()).isEqualTo(302);
        }

        List<UrlCheck> checks = UrlCheckRepository.findAllByUrlId(url.getId());
        assertThat(checks).hasSize(1);

        UrlCheck check = checks.get(0);
        assertThat(check.getStatusCode()).isEqualTo(200);
        assertThat(check.getTitle()).isEqualTo("Test Page");
        assertThat(check.getH1()).isEqualTo("Test Header");
        assertThat(check.getDescription()).isEqualTo("Test description");
    }

    @Test
    public void testUrlCheckWithError() throws IOException, SQLException {
        mockWebServer.enqueue(new MockResponse().setResponseCode(404));

        String testUrl = mockWebServer.url("/").toString().replaceAll("/$", "");
        String baseUrl = "http://localhost:" + app.port();

        RequestBody formBody = new FormBody.Builder()
            .add("url", testUrl)
            .build();

        Request postUrls = new Request.Builder()
            .url(baseUrl + "/urls")
            .post(formBody)
            .build();

        try (Response response = client.newCall(postUrls).execute()) {
            assertThat(response.code()).isEqualTo(302);
        }

        List<Url> urls = UrlRepository.getEntities();
        assertThat(urls).isNotEmpty();
        Url url = urls.get(0);

        Request postChecks = new Request.Builder()
            .url(baseUrl + "/urls/" + url.getId() + "/checks")
            .post(RequestBody.create(new byte[0]))
            .build();

        try (Response response = client.newCall(postChecks).execute()) {
            assertThat(response.code()).isEqualTo(302);
        }

        List<UrlCheck> checks = UrlCheckRepository.findAllByUrlId(url.getId());
        assertThat(checks).isEmpty();
    }
}
