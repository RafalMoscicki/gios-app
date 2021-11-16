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
    private final MailCreatorService mailCreatorService;

    public void sendInformationEmail(String baseUrl) {
        subscriptionRepository.findAll().stream()
                .filter(subscription -> subscription.getSubscriptionStatus().equals(SubscriptionStatus.ACTIVE))
                .forEach(subscription -> sendReportMail(subscription, baseUrl));
    }

    public void sendConfirmationEmail(Subscription subscription, String confirmationLink) {
        simpleEmailService.send(Mail.builder()
                .cityId(subscription.getCityId())
                .mailTo(subscription.getEmail())
                .subject("Subscription confirmation")
                .message(mailCreatorService.sendConfirmationEmail(confirmationLink)).build());
    }

    private void sendReportMail(Subscription subscription, String baseUrl) {
        String cancelLink = baseUrl + "/cancel?subscriptionId=" + subscription.getId() + "&token=" + subscription.getToken();
        simpleEmailService.send(
                Mail.builder()
                        .cityId(subscription.getCityId())
                        .mailTo(subscription.getEmail())
                        .subject(SUBJECT + giosCache.getCityName(subscription.getCityId()))
                        .message(mailCreatorService.buildReport(giosCache.getCityName(subscription.getCityId()), subscription.getCityId(), cancelLink))
                        .build()
        );
    }
}
