package api.gios.gov.pl.controller;

import api.gios.gov.pl.domain.subscribtion.SubscriptionDto;
import api.gios.gov.pl.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/v1/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService service;

    @PostMapping
    public void subscribe(@RequestBody SubscriptionDto subscription) {
        service.subscribe(subscription);
    }

    @PutMapping
    public void confirm(@RequestParam("subscriptionId") long subscriptionId,
                        @RequestParam("token") String token) {
        service.confirm(subscriptionId, token);
    }

    @DeleteMapping
    public void unsubscribe(@RequestParam("subscriptionId") long subscriptionId,
                            @RequestParam("token") String token) {
        service.unsubscribe(subscriptionId, token);
    }
}
