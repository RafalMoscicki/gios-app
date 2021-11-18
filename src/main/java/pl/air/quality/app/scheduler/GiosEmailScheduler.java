package pl.air.quality.app.scheduler;

import pl.air.quality.app.service.GiosEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GiosEmailScheduler {

    private final GiosEmailService service;

    @Scheduled(cron = "0 0 12 * * *")
    private void sendReports() {
        service.sendInformationEmail();
    }
}
