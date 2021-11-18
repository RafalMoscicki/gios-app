package pl.air_quality.app.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Builder
public class Mail {
    private final int cityId;
    private final String mailTo;
    private final String subject;
    private final String message;
}
