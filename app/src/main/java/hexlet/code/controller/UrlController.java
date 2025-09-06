package hexlet.code.controller;

import hexlet.code.dto.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.Unirest;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlController {

    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        var page = new UrlsPage(urls);

        Map<Long, UrlCheck> latestChecks = UrlCheckRepository.findLatestChecksForAllUrls();
        page.setLatestChecks(latestChecks);

        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flashType"));

        ctx.render("urls/index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        long id = ctx.pathParamAsClass("id", Long.class).get();
        Optional<Url> urlOptional = UrlRepository.find(id);

        if (urlOptional.isEmpty()) {
            throw new NotFoundResponse("URL не найден");
        }

        Url url = urlOptional.get();
        List<UrlCheck> checks = UrlCheckRepository.findAllByUrlId(id);

        var model = Map.of(
            "url", url,
            "checks", checks
        );
        ctx.render("urls/show.jte", model);
    }

    public static void create(Context ctx) {
        String inputUrl = ctx.formParam("url");
        if (inputUrl == null || inputUrl.trim().isEmpty()) {
            ctx.sessionAttribute("flash", "URL не может быть пустым");
            ctx.sessionAttribute("flashType", "danger");
            ctx.redirect("/");
            return;
        }

        String normalizedUrl;
        try {
            normalizedUrl = normalizeUrl(inputUrl);
        } catch (Exception ex) {
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
        } catch (SQLException e) {
            ctx.sessionAttribute("flash", "Ошибка при работе с базой данных");
            ctx.sessionAttribute("flashType", "danger");
            ctx.redirect("/");
        }
    }

    public static void check(Context ctx) throws SQLException {
        long id = ctx.pathParamAsClass("id", Long.class).get();
        Optional<Url> urlOptional = UrlRepository.find(id);

        if (urlOptional.isEmpty()) {
            throw new NotFoundResponse("URL не найден");
        }

        Url url = urlOptional.get();

        try {
            HttpResponse<String> response = Unirest.get(url.getName())
                                                   .connectTimeout(5000)
                                                   .asString();

            int statusCode = response.getStatus();

            if (statusCode == 200) {
                String body = response.getBody();
                Document doc = Jsoup.parse(body);

                String title = doc.title();
                String h1 = "";
                String description = "";

                Element h1Element = doc.selectFirst("h1");
                if (h1Element != null) {
                    h1 = h1Element.text().trim();
                }

                Element metaDescription = doc.selectFirst("meta[name=description]");
                if (metaDescription != null) {
                    description = metaDescription.attr("content").trim();
                }

                UrlCheck urlCheck = new UrlCheck();
                urlCheck.setUrlId(id);
                urlCheck.setStatusCode(statusCode);
                urlCheck.setTitle(title);
                urlCheck.setH1(h1);
                urlCheck.setDescription(description);
                urlCheck.setCreatedAt(new Timestamp(System.currentTimeMillis()));

                UrlCheckRepository.save(urlCheck);

                ctx.sessionAttribute("flash", "Страница успешно проверена");
                ctx.sessionAttribute("flashType", "success");
            } else {
                ctx.sessionAttribute("flash", "Не удалось проверить страницу: получен статус " + statusCode);
                ctx.sessionAttribute("flashType", "danger");
            }
        } catch (UnirestException e) {
            ctx.sessionAttribute("flash", "Невозможно проверить страницу: " + e.getMessage());
            ctx.sessionAttribute("flashType", "danger");
        }

        ctx.redirect("/urls/" + id);
    }

    private static String normalizeUrl(String inputUrl) throws URISyntaxException {
        var uri = new URI(inputUrl);
        String protocol = uri.getScheme();
        String host = uri.getHost();

        if (protocol == null || host == null) {
            throw new IllegalArgumentException("Некорректный URL");
        }

        int port = uri.getPort();
        if (port == -1
            || (protocol.equals("http") && port == 80)
            || (protocol.equals("https") && port == 443)) {
            return protocol + "://" + host;
        } else {
            return protocol + "://" + host + ":" + port;
        }
    }
}
