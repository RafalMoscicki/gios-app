package pl.air.quality.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.air.quality.app.domain.Mail;
import pl.air.quality.app.domain.subscribtion.Subscription;
import pl.air.quality.app.domain.subscribtion.SubscriptionStatus;
import pl.air.quality.app.exception.CityNotFoundException;
import pl.air.quality.app.gios.cache.GiosCache;
import pl.air.quality.app.repository.SubscriptionRepository;
import pl.air.quality.app.util.DateTimeUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class GiosEmailService {

    private static final String SUBJECT = "Air quality %s for ";

    private final SimpleEmailService simpleEmailService;
    private final SubscriptionRepository subscriptionRepository;
    private final GiosCache giosCache;
    private final ContentCreatorService contentCreatorService;

    public void sendInformationEmail() {
        subscriptionRepository.findAll().stream()
                .filter(subscription -> subscription.getSubscriptionStatus().equals(SubscriptionStatus.ACTIVE))
                .forEach(this::sendReportMail);
    }

    public void sendConfirmationEmail(Subscription subscription) {
        simpleEmailService.send(Mail.builder()
                .cityId(subscription.getCityId())
                .mailTo(subscription.getEmail())
                .subject("Subscription confirmation")
                .message(contentCreatorService.createConfirmation(subscription)).build());
    }

    private void sendReportMail(Subscription subscription) {
        try {
            simpleEmailService.send(
                    Mail.builder()
                            .cityId(subscription.getCityId())
                            .mailTo(subscription.getEmail())
                            .subject(String.format(SUBJECT, DateTimeUtils.parse(giosCache.getDateTime())) + giosCache.getCityName(subscription.getCityId()))
                            .message(contentCreatorService.createReport(giosCache.getCityName(subscription.getCityId()),
                                    subscription.getCityId(), subscription))
                            .build()
            );
        } catch (CityNotFoundException e) {
            log.error("Sending report error", e);
        }
    }
}
