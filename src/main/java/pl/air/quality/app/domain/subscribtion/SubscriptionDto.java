package pl.air.quality.app.domain.subscribtion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SubscriptionDto {

    private String email;
    private int cityId;
}
