package api.gios.gov.pl.scheduler;

import api.gios.gov.pl.service.GiosEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GiosEmailScheduler {

    private final GiosEmailService service;

    @Scheduled(cron = "* */2 * * * *")
    private void sendReports() {
        service.sendInformationEmail();
    }
}
