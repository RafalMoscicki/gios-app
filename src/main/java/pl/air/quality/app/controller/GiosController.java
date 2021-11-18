package pl.air.quality.app.controller;

import pl.air.quality.app.domain.CityDto;
import pl.air.quality.app.domain.report.ReportInfo;
import pl.air.quality.app.domain.sensor.ParamCodeDto;
import pl.air.quality.app.exception.CityNotFoundException;
import pl.air.quality.app.service.GiosServiceImpl;
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
    public Collection<ReportInfo> getReport(@RequestParam int cityId) {
        return service.getReport(cityId);
    }

    @GetMapping("/report/avg")
    public ReportInfo getAvgReport(@RequestParam int cityId) throws CityNotFoundException {
        return service.getAverageReport(cityId);
    }

    @GetMapping("/report/all")
    public List<ReportInfo> getAvgReportForAllCities() {
        return service.getAverageReportsForAllCities();
    }

    @GetMapping("/codes")
    public List<ParamCodeDto> getParamCodes() {
        return service.getParamCodes();
    }
}
