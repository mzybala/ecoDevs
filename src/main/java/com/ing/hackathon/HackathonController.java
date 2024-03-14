package com.ing.hackathon;

import com.ing.hackathon.apis.CarbonAwareSdkClient;
import com.ing.hackathon.models.Emissions;
import com.ing.hackathon.sci.SoftwareCarbonIntensity;
import com.ing.hackathon.sci.SoftwareCarbonIntensityService;
import com.ing.hackathon.watttime.Co2ResultDto;
import com.ing.hackathon.watttime.WattTimeClient;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/hackathon")
@AllArgsConstructor
public class HackathonController {
    private final WattTimeClient wattTimeClient;
    private CarbonAwareSdkClient client;
    private SoftwareCarbonIntensityService sciService;

    @GetMapping(value = "/carbon/footprint/index")
    public List<Co2ResultDto> getCo2Results(@RequestParam(required = false, name = "regions") List<String> regions) {
        if (regions.isEmpty()) {
            return List.of(wattTimeClient.getSignalIndex("FR"),
                    wattTimeClient.getSignalIndex("PL"),
                    wattTimeClient.getSignalIndex("DE"));
        } else {
            return regions.stream().map(wattTimeClient::getSignalIndex).collect(Collectors.toList());
        }
    }

    @GetMapping(value = "/sci")
    public ResponseEntity<SoftwareCarbonIntensity> sciEndpoint() {
        //return new ResponseEntity<>(new SoftwareCarbonIntensity(0.0, 0.0, 0.0), new HttpHeaders(), 200);
        return new ResponseEntity<>(sciService.getSoftwareCarbonIntensity(), new HttpHeaders(), 200);
    }

    @GetMapping(value = "/emissions")
    public ResponseEntity<List<Emissions>> emissions() {
        //return new ResponseEntity<>(List.of(new Emissions()), new HttpHeaders(), 200);
        return new ResponseEntity<>(client.emissionsForLocation(), new HttpHeaders(), 200);
    }
}
