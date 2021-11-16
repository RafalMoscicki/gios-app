package api.gios.gov.pl.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SubscriptionConfigConfig {

    @Value("${subscription.url}")
    private String subscriptionUrl;
}
