package api.gios.gov.pl.service;

import api.gios.gov.pl.domain.subscribtion.Subscription;
import api.gios.gov.pl.domain.subscribtion.SubscriptionDto;
import api.gios.gov.pl.domain.subscribtion.SubscriptionStatus;
import api.gios.gov.pl.exception.SubscriptionDoesNotExistException;
import api.gios.gov.pl.exception.SubscriptionExistException;
import api.gios.gov.pl.exception.SubscriptionTokenNotValidException;
import api.gios.gov.pl.repository.SubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        Subscription newSubscription = new Subscription(subscription.getCityId(), subscription.getEmail(), SubscriptionStatus.PENDING, UUID.randomUUID().toString());
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
    