package pl.air.quality.app.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.air.quality.app.domain.subscribtion.SubscriptionStatus;
import pl.air.quality.app.repository.SubscriptionRepository;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubscriptionCleanerScheduler {

    private final SubscriptionRepository subscriptionRepository;

    @Scheduled(cron = "0 0/5 * * * *")
    private void cleanNotConfirmedSubscriptions() {
        log.info("Cleaning not confirmed subscriptions");
        subscriptionRepository.findBySubscriptionStatusAndSubscriptionDateBefore(
                SubscriptionStatus.PENDING,
                LocalDateTime.now().minusMinutes(5))
                    .forEach(subscription -> subscriptionRepository.deleteById(subscription.getId()));
    }
}
