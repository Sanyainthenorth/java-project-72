package gg.jte.generated.ondemand.urls;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import java.util.List;
import java.time.format.DateTimeFormatter;
@SuppressWarnings("unchecked")
public final class JteshowGenerated {
	public static final String JTE_NAME = "urls/show.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,3,5,5,5,5,13,13,13,13,112,112,112,114,120,120,120,124,124,124,128,128,128,135,139,139,139,139,144,144,158,158,160,160,160,161,161,161,162,162,162,163,163,163,164,164,164,165,165,165,167,167,171,171,175,175,187,187,187,5,6,6,6,6};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Url url, List<UrlCheck> checks) {
		jteOutput.writeContent("\n<!DOCTYPE html>\n<html lang=\"ru\">\n<head>\n    <meta charset=\"UTF-8\">\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n    <title>Анализатор страниц - ");
		jteOutput.setContext("title", null);
		jteOutput.writeUserContent(url.getName());
		jteOutput.writeContent("</title>\n    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\n    <style>\n        body {\n            background-color: #f8f9fa;\n            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\n        }\n        .navbar {\n            background-color: #fff;\n            box-shadow: 0 2px 4px rgba(0,0,0,0.1);\n            padding: 15px 0;\n        }\n        .navbar-brand {\n            font-weight: 600;\n            color: #333;\n            font-size: 1.5rem;\n        }\n        .nav-link {\n            color: #6c757d;\n            font-weight: 500;\n            margin-right: 20px;\n        }\n        .nav-link.active {\n            color: #0d6efd;\n        }\n        .content-section {\n            background-color: #fff;\n            border-radius: 10px;\n            box-shadow: 0 4px 12px rgba(0,0,0,0.05);\n            padding: 30px;\n            margin-top: 30px;\n        }\n        .section-title {\n            font-weight: 600;\n            margin-bottom: 20px;\n            color: #333;\n        }\n        .info-table {\n            width: 100%;\n            border-collapse: collapse;\n        }\n        .info-table th {\n            text-align: left;\n            padding: 12px;\n            background-color: #f8f9fa;\n            border-bottom: 1px solid #dee2e6;\n            width: 200px;\n        }\n        .info-table td {\n            padding: 12px;\n            border-bottom: 1px solid #dee2e6;\n        }\n        .checks-table {\n            width: 100%;\n            border-collapse: collapse;\n        }\n        .checks-table th {\n            background-color: #f8f9fa;\n            padding: 12px;\n            text-align: left;\n            border-bottom: 2px solid #dee2e6;\n            font-weight: 600;\n        }\n        .checks-table td {\n            padding: 12px;\n            border-bottom: 1px solid #dee2e6;\n        }\n        .btn-primary {\n            padding: 10px 25px;\n            border-radius: 8px;\n            font-weight: 500;\n        }\n        .footer {\n            text-align: center;\n            margin-top: 40px;\n            color: #6c757d;\n            font-size: 0.9rem;\n        }\n        .no-checks {\n            text-align: center;\n            padding: 40px;\n            color: #6c757d;\n            font-style: italic;\n        }\n    </style>\n</head>\n<body>\n    <nav class=\"navbar\">\n        <div class=\"container\">\n            <span class=\"navbar-brand mb-0 h1\">Анализатор страниц</span>\n            <div class=\"d-flex\">\n                <a href=\"/\" class=\"nav-link\">Главная</a>\n                <a href=\"/urls\" class=\"nav-link active\">Сайты</a>\n            </div>\n        </div>\n    </nav>\n\n    <div class=\"container\">\n        <div class=\"content-section\">\n            <h1 class=\"section-title\">Сайт: ");
		jteOutput.setContext("h1", null);
		jteOutput.writeUserContent(url.getName());
		jteOutput.writeContent("</h1>\n\n            ");
		jteOutput.writeContent("\n            <div class=\"mb-4\">\n                <h3 class=\"mb-3\">Информация о сайте</h3>\n                <table class=\"info-table\">\n                    <tr>\n                        <th>ID</th>\n                        <td>");
		jteOutput.setContext("td", null);
		jteOutput.writeUserContent(url.getId());
		jteOutput.writeContent("</td>\n                    </tr>\n                    <tr>\n                        <th>Имя</th>\n                        <td>");
		jteOutput.setContext("td", null);
		jteOutput.writeUserContent(url.getName());
		jteOutput.writeContent("</td>\n                    </tr>\n                    <tr>\n                        <th>Дата создания</th>\n                        <td>");
		jteOutput.setContext("td", null);
		jteOutput.writeUserContent(url.getCreatedAt().toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
		jteOutput.writeContent("</td>\n                    </tr>\n                </table>\n            </div>\n\n            <hr class=\"my-4\">\n\n            ");
		jteOutput.writeContent("\n            <div>\n                <div class=\"d-flex justify-content-between align-items-center mb-4\">\n                    <h3 class=\"mb-0\">Проверки</h3>\n                    <form action=\"/urls/");
		jteOutput.setContext("form", "action");
		jteOutput.writeUserContent(url.getId());
		jteOutput.setContext("form", null);
		jteOutput.writeContent("/checks\" method=\"post\">\n                        <button type=\"submit\" class=\"btn btn-primary\">Запустить проверку</button>\n                    </form>\n                </div>\n\n                ");
		if (!checks.isEmpty()) {
			jteOutput.writeContent("\n                <div class=\"table-responsive\">\n                    <table class=\"checks-table\">\n                        <thead>\n                            <tr>\n                                <th>ID</th>\n                                <th>Код ответа</th>\n                                <th>Заголовок</th>\n                                <th>H1</th>\n                                <th>Описание</th>\n                                <th>Дата проверки</th>\n                            </tr>\n                        </thead>\n                        <tbody>\n                            ");
			for (var check : checks) {
				jteOutput.writeContent("\n                            <tr>\n                                <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(check.getId());
				jteOutput.writeContent("</td>\n                                <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(check.getStatusCode());
				jteOutput.writeContent("</td>\n                                <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(check.getTitle());
				jteOutput.writeContent("</td>\n                                <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(check.getH1());
				jteOutput.writeContent("</td>\n                                <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(check.getDescription());
				jteOutput.writeContent("</td>\n                                <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(check.getCreatedAt().toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
				jteOutput.writeContent("</td>\n                            </tr>\n                            ");
			}
			jteOutput.writeContent("\n                        </tbody>\n                    </table>\n                </div>\n                ");
		} else {
			jteOutput.writeContent("\n                <div class=\"no-checks\">\n                    <p>Проверок еще не было</p>\n                </div>\n                ");
		}
		jteOutput.writeContent("\n            </div>\n        </div>\n    </div>\n\n    <div class=\"footer\">\n        created by Sasha\n    </div>\n\n    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js\"></script>\n</body>\n</html>\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Url url = (Url)params.get("url");
		List<UrlCheck> checks = (List<UrlCheck>)params.get("checks");
		render(jteOutput, jteHtmlInterceptor, url, checks);
	}
}
