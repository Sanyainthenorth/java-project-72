package hexlet.code;

import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static Javalin getApp() {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "7000"));

        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
        });

        app.before(ctx -> ctx.contentType("text/html; charset=utf-8")); // кодировка

        app.get("/", ctx -> ctx.result("Hello World"));
        

        return app;
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start();
        logger.info("App started on port " + app.port());
    }
}
