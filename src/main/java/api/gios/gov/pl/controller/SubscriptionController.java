package api.gios.gov.pl.controller;

import api.gios.gov.pl.domain.subscribtion.SubscriptionDto;
import api.gios.gov.pl.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    @PostMapping("/confirmation")
    public String confirm(@RequestParam("subscriptionId") long subscriptionId,
                        @RequestParam("token") String token) {
        return service.confirm(subscriptionId, token);
    }

    @PostMapping("/cancellation")
    public String unsubscribe(@RequestParam("subscriptionId") long subscriptionId,
                            @RequestParam("token") String token) {
        return service.unsubscribe(subscriptionId, token);
    }
}
