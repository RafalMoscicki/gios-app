package api.gios.gov.pl.scheduler;

import api.gios.gov.pl.gios.cache.GiosCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GiosDataScheduler {

    private final GiosCache giosCache;

    @Scheduled(cron = "* 5 * * * *")
    private void loadData() {
        log.info("fetching new data");
        giosCache.loadGiosData();
    }
}
