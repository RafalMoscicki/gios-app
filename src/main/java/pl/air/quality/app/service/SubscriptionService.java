package pl.air.quality.app.service;

import pl.air.quality.app.domain.subscribtion.SubscriptionDto;
import pl.air.quality.app.exception.SubscriptionDoesNotExistException;
import pl.air.quality.app.domain.subscribtion.Subscription;
import pl.air.quality.app.domain.subscribtion.SubscriptionStatus;
import pl.air.quality.app.exception.SubscriptionExistException;
import pl.air.quality.app.exception.SubscriptionTokenNotValidException;
import pl.air.quality.app.repository.SubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository repository;

    @Autowired
    private GiosEmailService giosEmailService;

    @Autowired
    private ContentCreatorService contentCreatorService;

    public void subscribe(SubscriptionDto subscription) {
        List<Subscription> byEmailAndCityId = repository.findByEmailAndCityId(
                subscription.getEmail(),
                subscription.getCityId());
        if (byEmailAndCityId.size() > 0) {
            throw new SubscriptionExistException(subscription.getEmail(), subscription.getCityId());
        }
        Subscription newSubscription = new Subscription(
                subscription.getCityId(),
                subscription.getEmail(),
                SubscriptionStatus.PENDING,
                UUID.randomUUID().toString()
        );
        log.info("subscription " + subscription + " created");
        repository.save(newSubscription);
        giosEmailService.sendConfirmationEmail(newSubscription);
    }

    public String confirm(long subscriptionId, String token) {
        Optional<Subscription> maybeSubscription = repository.findById(subscriptionId);
        if (maybeSubscription.isPresent()) {
            Subscription subscription = maybeSubscription.get();
            if (subscription.getToken().substring(0, 36).equals(token)) {
                subscription.setSubscriptionStatus(SubscriptionStatus.ACTIVE);
                subscription.setSubscriptionDate(LocalDateTime.now());
                log.info("subscription " + subscriptionId + " confirmed");
                return contentCreatorService.createResponseMessage("Subskrypcja potwierdzona");
            } else {
                throw new SubscriptionTokenNotValidException(token);
            }
        } else {
            throw new SubscriptionDoesNotExistException(subscriptionId);
        }
    }

    public String unsubscribe(long subscriptionId, String token) {
        Optional<Subscription> maybeSubscription = repository.findById(subscriptionId);
        if (maybeSubscription.isPresent()) {
            Subscription subscription = maybeSubscription.get();
            if (subscription.getToken().substring(0, 36).equals(token)) {
                log.info("subscription " + subscriptionId + " deleted");
                repository.deleteById(subscriptionId);
                return contentCreatorService.createResponseMessage("Subskrypcja anulowana");
            } else {
                throw new SubscriptionTokenNotValidException(token);
            }
        } else {
            throw new SubscriptionDoesNotExistException(subscriptionId);
        }
    }
}
    