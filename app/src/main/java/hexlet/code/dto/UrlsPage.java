package hexlet.code.dto;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public final class UrlsPage {
    private List<Url> urls;
    private Map<Long, UrlCheck> latestChecks = new HashMap<>();
    private String flash;
    private String flashType;

    public UrlsPage(List<Url> urls) {
        this.urls = urls;
    }

    // Геттеры и сеттеры
    public List<Url> getUrls() {
        return urls;
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }

    public Map<Long, UrlCheck> getLatestChecks() {
        return latestChecks;
    }

    public void setLatestChecks(Map<Long, UrlCheck> latestChecks) {
        this.latestChecks = latestChecks;
    }

    public String getFlash() {
        return flash;
    }

    public void setFlash(String flash) {
        this.flash = flash;
    }

    public String getFlashType() {
        return flashType;
    }

    public void setFlashType(String flashType) {
        this.flashType = flashType;
    }
}
