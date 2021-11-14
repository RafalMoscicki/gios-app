package api.gios.gov.pl.repository;

import api.gios.gov.pl.domain.subscribtion.Subscription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

    @Override
    Subscription save(Subscription subscription);

    @Override
    List<Subscription> findAll();

    @Override
    void deleteById(Long id);

    List<Subscription> findByEmailAndCityId(String email, int cityId);

    @Override
    Optional<Subscription> findById(Long subscriptionId);
}
