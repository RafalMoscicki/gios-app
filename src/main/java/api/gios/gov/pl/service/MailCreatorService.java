package api.gios.gov.pl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailCreatorService {

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String sendOneMsgPerDay(String message) {
        Context context = new Context();
        return templateEngine.process("mail/tasks-number-in-crud-app", context);
    }
}
