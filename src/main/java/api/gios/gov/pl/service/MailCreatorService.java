package api.gios.gov.pl.service;

import api.gios.gov.pl.domain.GiosInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class MailCreatorService {

    private final TemplateEngine templateEngine;
    private final GiosService giosService;

    public String buildReport(String cityName, int cityId, String cancelLink) {

        GiosInfo giosReport = giosService.getAverageReport(cityId);

        Context context = new Context();
        context.setVariable("cityName", cityName);
        context.setVariable("gios_report", giosReport.getValues());
        context.setVariable("cancelLink", cancelLink);
        return templateEngine.process("mail/gios-report", context);
    }

    public String  sendConfirmationEmail(String link) {
        Context context = new Context();
        context.setVariable("link", link);
        return templateEngine.process("mail/gios-token-confirmation-email", context);
    }

//    public String sendOneMsgPerDay(String message) {
//        Context context = new Context();
//        context.setVariable("message", message);
//        context.setVariable("tasks_url", "http://localhost:8888/task");
//        context.setVariable("button", "Visit website");
//        context.setVariable("preview", "message generated automatically");
//        context.setVariable("goodbye", "Thanks for using our CRUD APP");
//        context.setVariable("show_button", false);
//        context.setVariable("is_friend", false);
//        context.setVariable("admin_config", adminConfig);
//        context.setVariable("application_functionality", functionality);
//        return templateEngine.process("mail/tasks-number-in-crud-app", context);
//    }
}
