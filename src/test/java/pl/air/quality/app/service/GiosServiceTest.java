package pl.air.quality.app.service;

import pl.air.quality.app.domain.report.ReportInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.air.quality.app.exception.StationNotFoundException;

@SpringBootTest
class GiosServiceTest {

    @Autowired
    private GiosServiceImpl giosService;

    @Test
    public void test() throws StationNotFoundException {
        ReportInfo info = giosService.getInfo(52);
        Assertions.assertNotNull(info);
    }

}