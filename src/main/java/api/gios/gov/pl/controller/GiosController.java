package api.gios.gov.pl.controller;

import api.gios.gov.pl.domain.CityDto;
import api.gios.gov.pl.domain.GiosInfo;
import api.gios.gov.pl.domain.sensor.ParamCodeDto;
import api.gios.gov.pl.service.GiosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/v1/gios")
public class GiosController {

    private final GiosService service;

    @Autowired
    public GiosController(GiosService service) {
        this.service = service;
    }

    @GetMapping("/city")
    public Set<CityDto> getCities() {
        return service.getAllCities();
    }

    @GetMapping("/report")
    public Collection<GiosInfo> getReport(@RequestParam int cityId) {
        return service.getReport(cityId);
    }

    @GetMapping("/report/avg")
    public GiosInfo getAvgReport(@RequestParam int cityId) {
        return service.getAverageReport(cityId);
    }

    @GetMapping("/report/all")
    public List<GiosInfo> getAvgReportForAllCities() {
        return service.getAverageReportsForAllCities();
    }

    @GetMapping("/codes")
    public List<ParamCodeDto> getParamCodes() {
        return service.getParamCodes();
    }
}
