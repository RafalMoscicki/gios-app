package pl.air_quality.app.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.support.CronExpression;
import org.thymeleaf.TemplateEngine;
import pl.air_quality.app.config.AppConfig;
import pl.air_quality.app.domain.subscribtion.Subscription;
import pl.air_quality.app.exception.CityNotFoundException;
import pl.air_quality.app.gios.cache.GiosCache;
import pl.air_quality.app.gios.cache.MockGiosCache;
import pl.air_quality.app.util.DateTimeUtils;

import java.time.LocalDateTime;

class MailCreatorServiceTest {

    private final TemplateEngine templateEngine = new TemplateEngine();
    private final AppConfig appConfig = new AppConfig();
    private final GiosCache giosCache = new MockGiosCache();
    private final GiosServiceImpl giosService = new GiosServiceImpl(giosCache);
    private final ContentCreatorService testee = new ContentCreatorService(templateEngine, giosService, appConfig);

    @Test
    public void testMailCreation() throws CityNotFoundException {
        Subscription subscription = new Subscription();
        int cityId = 1;
        String cityName = giosCache.getCityName(1);
        String message = testee.createReport(cityName, cityId, subscription);
        System.out.println(message);
        Assertions.assertNotNull(message);
    }
    @Test
    public void croneTest() {
        CronExpression cronExpression = CronExpression.parse("0 5 * * * *");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next = cronExpression.next(now);
        LocalDateTime next1 = cronExpression.next(next);
        System.out.println(DateTimeUtils.parse(now) + " - " + DateTimeUtils.parse(next) + " - " + DateTimeUtils.parse(next1));
    }
}