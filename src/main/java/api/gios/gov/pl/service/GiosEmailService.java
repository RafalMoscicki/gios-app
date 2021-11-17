package api.gios.gov.pl.service;

import api.gios.gov.pl.domain.Mail;
import api.gios.gov.pl.domain.subscribtion.Subscription;
import api.gios.gov.pl.domain.subscribtion.SubscriptionStatus;
import api.gios.gov.pl.gios.cache.GiosCache;
import api.gios.gov.pl.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GiosEmailService {

    private static final String SUBJECT = "Air quality " + LocalDateTime.now() + " for ";

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
        simpleEmailService.send(
                Mail.builder()
                        .cityId(subscription.getCityId())
                        .mailTo(subscription.getEmail())
                        .subject(SUBJECT + giosCache.getCityName(subscription.getCityId()))
                        .message(contentCreatorService.createReport(giosCache.getCityName(subscription.getCityId()), subscription.getCityId(), subscription))
                        .build()
        );
    }
}
