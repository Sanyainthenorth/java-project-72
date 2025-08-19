package hexlet.code.dto;

import hexlet.code.model.Url;
import java.util.List;

public class UrlsPage {
    private List<Url> urls;
    private String flash;
    private String flashType;

    public UrlsPage(List<Url> urls) {
        this.urls = urls;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
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