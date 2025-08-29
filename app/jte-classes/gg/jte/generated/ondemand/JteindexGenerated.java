package gg.jte.generated.ondemand;
@SuppressWarnings("unchecked")
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,10,10,91,92,92,93,93,93,93,94,94,94,97,97,98,124,124,124,0,0,0,0};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, hexlet.code.dto.MainPage page) {
		jteOutput.writeContent("\n<!DOCTYPE html>\n<html lang=\"ru\">\n<head>\n    <meta charset=\"UTF-8\">\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n    <title>Анализатор страниц</title>\n    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\n    <style>\n        ");
		jteOutput.writeContent("\n        body {\n            background-color: #f8f9fa;\n            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\n        }\n        .navbar {\n            background-color: #fff;\n            box-shadow: 0 2px 4px rgba(0,0,0,0.1);\n            padding: 15px 0;\n        }\n        .navbar-brand {\n            font-weight: 600;\n            color: #333;\n            font-size: 1.5rem;\n        }\n        .nav-link {\n            color: #6c757d;\n            font-weight: 500;\n            margin-right: 20px;\n        }\n        .nav-link.active {\n            color: #0d6efd;\n        }\n        .hero-section {\n            background-color: #fff;\n            border-radius: 10px;\n            box-shadow: 0 4px 12px rgba(0,0,0,0.05);\n            padding: 40px;\n            margin-top: 30px;\n            text-align: center;\n        }\n        .hero-title {\n            font-weight: 600;\n            margin-bottom: 20px;\n            color: #333;\n        }\n        .hero-subtitle {\n            color: #6c757d;\n            margin-bottom: 30px;\n            font-size: 1.1rem;\n        }\n        .url-input-container {\n            max-width: 600px;\n            margin: 0 auto;\n        }\n        .form-control {\n            padding: 12px 16px;\n            border-radius: 8px;\n            font-size: 1rem;\n        }\n        .btn-primary {\n            padding: 12px 30px;\n            border-radius: 8px;\n            font-weight: 500;\n            margin-top: 15px;\n        }\n        .example-text {\n            color: #6c757d;\n            font-size: 0.9rem;\n            margin-top: 10px;\n        }\n        .footer {\n            text-align: center;\n            margin-top: 40px;\n            color: #6c757d;\n            font-size: 0.9rem;\n        }\n    </style>\n</head>\n<body>\n    <nav class=\"navbar\">\n        <div class=\"container\">\n            <span class=\"navbar-brand mb-0 h1\">Анализатор страниц</span>\n            <div class=\"d-flex\">\n                <a href=\"/\" class=\"nav-link active\">Главная</a>\n                <a href=\"/urls\" class=\"nav-link\">Сайты</a>\n            </div>\n        </div>\n    </nav>\n\n    <div class=\"container\">\n        ");
		jteOutput.writeContent("\n        ");
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
		jteOutput.writeContent("\n        ");
		jteOutput.writeContent("\n\n        <div class=\"hero-section\">\n            <h1 class=\"hero-title\">Анализатор страниц</h1>\n            <p class=\"hero-subtitle\">Бесплатно проверяйте сайты на SEO пригодность</p>\n\n            <div class=\"url-input-container\">\n                <form action=\"/urls\" method=\"post\">\n                    <div class=\"mb-3\">\n                        <label for=\"urlInput\" class=\"form-label\">Ссылка</label>\n                        <input type=\"text\" class=\"form-control\" id=\"urlInput\" name=\"url\"\n                               placeholder=\"Введите URL сайта\" required>\n                    </div>\n                    <button type=\"submit\" class=\"btn btn-primary\">Проверить</button>\n                    <div class=\"example-text\">Пример: https://www.example.com</div>\n                </form>\n            </div>\n        </div>\n    </div>\n\n    <div class=\"footer\">\n        created by Sasha\n    </div>\n\n    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js\"></script>\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		hexlet.code.dto.MainPage page = (hexlet.code.dto.MainPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
