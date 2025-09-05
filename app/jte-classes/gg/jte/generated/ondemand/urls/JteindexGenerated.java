package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.UrlsPage;
import hexlet.code.model.Url;
import java.time.format.DateTimeFormatter;
@SuppressWarnings("unchecked")
public final class JteindexGenerated {
	public static final String JTE_NAME = "urls/index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,4,4,4,4,86,86,86,87,87,87,87,88,88,88,91,91,96,96,108,108,110,110,110,111,111,111,111,111,111,111,113,113,114,114,114,115,115,117,117,120,120,121,121,121,122,122,124,124,127,127,131,131,135,135,145,145,145,4,4,4,4};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlsPage page) {
		jteOutput.writeContent("\n<!DOCTYPE html>\n<html lang=\"ru\">\n<head>\n    <meta charset=\"UTF-8\">\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n    <title>Анализатор страниц - Сайты</title>\n    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\n    <style>\n        body {\n            background-color: #f8f9fa;\n            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\n        }\n        .navbar {\n            background-color: #fff;\n            box-shadow: 0 2px 4px rgba(0,0,0,0.1);\n            padding: 15px 0;\n        }\n        .navbar-brand {\n            font-weight: 600;\n            color: #333;\n            font-size: 1.5rem;\n        }\n        .nav-link {\n            color: #6c757d;\n            font-weight: 500;\n            margin-right: 20px;\n        }\n        .nav-link.active {\n            color: #0d6efd;\n        }\n        .content-section {\n            background-color: #fff;\n            border-radius: 10px;\n            box-shadow: 0 4px 12px rgba(0,0,0,0.05);\n            padding: 30px;\n            margin-top: 30px;\n        }\n        .section-title {\n            font-weight: 600;\n            margin-bottom: 25px;\n            color: #333;\n            font-size: 1.8rem;\n        }\n        .table th {\n            border-top: none;\n            font-weight: 600;\n            color: #495057;\n            background-color: #f8f9fa;\n            padding: 15px;\n        }\n        .table td {\n            padding: 15px;\n            vertical-align: middle;\n        }\n        .footer {\n            text-align: center;\n            margin-top: 40px;\n            color: #6c757d;\n            font-size: 0.9rem;\n        }\n        .no-data {\n            text-align: center;\n            padding: 40px;\n            color: #6c757d;\n            font-style: italic;\n        }\n    </style>\n</head>\n<body>\n    <nav class=\"navbar\">\n        <div class=\"container\">\n            <span class=\"navbar-brand mb-0 h1\">Анализатор страниц</span>\n            <div class=\"d-flex\">\n                <a href=\"/\" class=\"nav-link\">Главная</a>\n                <a href=\"/urls\" class=\"nav-link active\">Сайты</a>\n            </div>\n        </div>\n    </nav>\n\n    <div class=\"container\">\n        ");
		if (page.getFlash() != null) {
			jteOutput.writeContent("\n        <div class=\"alert alert-");
			jteOutput.setContext("div", "class");
			jteOutput.writeUserContent(page.getFlashType());
			jteOutput.setContext("div", null);
			jteOutput.writeContent(" alert-dismissible fade show mt-3\">\n            ");
			jteOutput.setContext("div", null);
			jteOutput.writeUserContent(page.getFlash());
			jteOutput.writeContent("\n            <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\"></button>\n        </div>\n        ");
		}
		jteOutput.writeContent("\n\n        <div class=\"content-section\">\n            <h1 class=\"section-title\">Сайты</h1>\n\n            ");
		if (page.getUrls() != null && !page.getUrls().isEmpty()) {
			jteOutput.writeContent("\n            <div class=\"table-responsive\">\n                <table class=\"table table-bordered table-hover\">\n                    <thead>\n                        <tr>\n                            <th>ID</th>\n                            <th>Имя</th>\n                            <th>Последняя проверка</th>\n                            <th>Код ответа</th>\n                        </tr>\n                    </thead>\n                    <tbody>\n                        ");
			for (var url : page.getUrls()) {
				jteOutput.writeContent("\n                        <tr>\n                            <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(url.getId());
				jteOutput.writeContent("</td>\n                            <td><a href=\"/urls/");
				jteOutput.setContext("a", "href");
				jteOutput.writeUserContent(url.getId());
				jteOutput.setContext("a", null);
				jteOutput.writeContent("\" class=\"text-decoration-none\">");
				jteOutput.setContext("a", null);
				jteOutput.writeUserContent(url.getName());
				jteOutput.writeContent("</a></td>\n                            <td>\n                                ");
				if (page.getLatestChecks() != null && page.getLatestChecks().containsKey(url.getId())) {
					jteOutput.writeContent("\n                                    ");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(page.getLatestChecks().get(url.getId()).getCreatedAt().toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
					jteOutput.writeContent("\n                                ");
				} else {
					jteOutput.writeContent("\n                                    <span class=\"text-muted\">—</span>\n                                ");
				}
				jteOutput.writeContent("\n                            </td>\n                            <td>\n                                ");
				if (page.getLatestChecks() != null && page.getLatestChecks().containsKey(url.getId())) {
					jteOutput.writeContent("\n                                    ");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(page.getLatestChecks().get(url.getId()).getStatusCode());
					jteOutput.writeContent("\n                                ");
				} else {
					jteOutput.writeContent("\n                                    <span class=\"text-muted\">—</span>\n                                ");
				}
				jteOutput.writeContent("\n                            </td>\n                        </tr>\n                        ");
			}
			jteOutput.writeContent("\n                    </tbody>\n                </table>\n            </div>\n            ");
		} else {
			jteOutput.writeContent("\n            <div class=\"no-data\">\n                <p>Пока нет добавленных сайтов</p>\n            </div>\n            ");
		}
		jteOutput.writeContent("\n        </div>\n    </div>\n\n    <div class=\"footer\">\n        created by Sasha\n    </div>\n\n    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js\"></script>\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlsPage page = (UrlsPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
