package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class UrlCheck {
    private Long id;
    private Integer statusCode;
    private String title;
    private String h1;
    private String description;
    private Long urlId;
    private Timestamp createdAt;
}

