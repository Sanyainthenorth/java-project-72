package gg.jte.generated.ondemand;
@SuppressWarnings("unchecked")
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,11,11,11,12,12,12,12,12,12,12,13,13,24,24,24,0,0,0,0};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, hexlet.code.dto.MainPage page) {
		jteOutput.writeContent("\n<!DOCTYPE html>\n<html>\n<head>\n    <meta charset=\"UTF-8\">\n    <title>URL Checker</title>\n    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\n</head>\n<body>\n<div class=\"container mt-5\">\n    ");
		if (page.getFlash() != null) {
			jteOutput.writeContent("\n        <div class=\"alert alert-");
			jteOutput.setContext("div", "class");
			jteOutput.writeUserContent(page.getFlashType());
			jteOutput.setContext("div", null);
			jteOutput.writeContent("\">");
			jteOutput.setContext("div", null);
			jteOutput.writeUserContent(page.getFlash());
			jteOutput.writeContent("</div>\n    ");
		}
		jteOutput.writeContent("\n\n    <h1>Добавить новый URL</h1>\n    <form action=\"/urls\" method=\"post\">\n        <div class=\"mb-3\">\n            <input type=\"text\" name=\"url\" class=\"form-control\" placeholder=\"https://example.com\" required>\n        </div>\n        <button type=\"submit\" class=\"btn btn-primary\">Добавить</button>\n    </form>\n</div>\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		hexlet.code.dto.MainPage page = (hexlet.code.dto.MainPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
