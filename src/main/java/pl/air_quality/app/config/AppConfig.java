package pl.air_quality.app.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AppConfig {

    @Value("${baseUrl}")
    private String baseUrl;

    @Value("${frontend.url}")
    private String frontendUrl;
}
