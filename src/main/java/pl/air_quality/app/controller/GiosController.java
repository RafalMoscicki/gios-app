package pl.air_quality.app.controller;

import pl.air_quality.app.domain.CityDto;
import pl.air_quality.app.domain.GiosInfo;
import pl.air_quality.app.domain.sensor.ParamCodeDto;
import pl.air_quality.app.exception.CityNotFoundException;
import pl.air_quality.app.service.GiosServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/v1/gios")
public class GiosController {

    private final GiosServiceImpl service;

    @Autowired
    public GiosController(GiosServiceImpl service) {
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
    public GiosInfo getAvgReport(@RequestParam int cityId) throws CityNotFoundException {
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
