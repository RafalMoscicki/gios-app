package pl.air.quality.app.service;

import pl.air.quality.app.config.AppConfig;
import pl.air.quality.app.domain.report.ReportInfo;
import pl.air.quality.app.domain.subscribtion.Subscription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentCreatorService {

    private final TemplateEngine templateEngine;
    private final GiosService giosService;
    private final AppConfig appConfig;

    public String createReport(String cityName, int cityId, Subscription subscription) {
        String cancelLink = appConfig.getBaseUrl() + "/v1/subscription/cancellation?subscriptionId="
                + subscription.getId() + "&token=" + subscription.getToken();
        List<ReportInfo> giosReports = giosService.getReport(cityId);
        ReportInfo averageReport = giosService.getAverageReport(cityId);
        Context context = new Context();
        context.setVariable("cityName", cityName);
        context.setVariable("dateTime", giosService.getDateTime());
        context.setVariable("average_report", averageReport.getValues());
        context.setVariable("gios_reports", giosReports);
        context.setVariable("cancelLink", cancelLink);
        return templateEngine.process("mail/report", context);
    }

    public String createConfirmation(Subscription subscription) {
        String confirmLink = appConfig.getBaseUrl() + "/v1/subscription/confirmation?subscriptionId="
                + subscription.getId() + "&token=" + subscription.getToken();

        Context context = new Context();
        context.setVariable("confirmLink", confirmLink);
        return templateEngine.process("mail/confirmation", context);
    }

    public String createResponseMessage(String message) {
        Context context = new Context();
        context.setVariable("link", appConfig.getFrontendUrl());
        context.setVariable("message", message);
        return templateEngine.process("message/response_message", context);
    }
}
