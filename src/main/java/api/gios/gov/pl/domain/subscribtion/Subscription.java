package api.gios.gov.pl.domain.subscribtion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity(name = "subscriptions")
public class Subscription implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "city_id")
    private int cityId;

    private String email;

    @Column(name = "subscription_status")
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus subscriptionStatus;

    private String token;

    public Subscription(int cityId, String email, SubscriptionStatus subscriptionStatus, String token) {
        this.cityId = cityId;
        this.email = email;
        this.subscriptionStatus = subscriptionStatus;
        this.token = token;
    }

    public void setSubscriptionStatus(SubscriptionStatus subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
