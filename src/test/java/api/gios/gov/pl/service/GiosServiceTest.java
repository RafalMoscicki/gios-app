package api.gios.gov.pl.service;

import api.gios.gov.pl.domain.GiosInfo;
import api.gios.gov.pl.gios.MockGiosCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GiosServiceTest {

    @Autowired
    private GiosService giosService;

    @Test
    public void test() {
        GiosInfo info = giosService.getInfo(52);
        Assertions.assertNotNull(info);
    }

}