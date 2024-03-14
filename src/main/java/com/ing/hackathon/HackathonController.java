package com.ing.hackathon;

import com.ing.hackathon.cloud.CloudProviderService;
import com.ing.hackathon.cloud.CloudProvidersResponseDto;
import com.ing.hackathon.cloud.DeploymentsResponseDto;
import com.ing.hackathon.cloud.PubSubPublisher;
import com.ing.hackathon.apis.CarbonAwareSdkClient;
import com.ing.hackathon.models.Emissions;
import com.ing.hackathon.sci.SoftwareCarbonIntensity;
import com.ing.hackathon.sci.SoftwareCarbonIntensityService;
import com.ing.hackathon.watttime.Co2ResultDto;
import com.ing.hackathon.watttime.WattTimeClient;
import com.ing.hackathon.watttime.WattTimeUtil;
import com.ing.hackathon.watttime.WatttimeException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/hackathon")
@AllArgsConstructor
@Slf4j
public class HackathonController {
    private final WattTimeClient wattTimeClient;
    private final PubSubPublisher pubSubPublisher;
    private final WattTimeUtil wattTimeUtil;
    private final CloudProviderService cloudProviderService;
    private CarbonAwareSdkClient client;
    private SoftwareCarbonIntensityService sciService;

    @GetMapping(value = "/carbon/footprint/index")
    public List<Co2ResultDto> getCo2Results(@RequestParam(required = false, name = "regions") List<String> regions) {
        if (regions.isEmpty()) {
            return wattTimeUtil.getRegions().stream().map(wattTimeClient::getSignalIndex).collect(Collectors.toList());
        } else {
            return regions.stream().map(wattTimeClient::getSignalIndex).collect(Collectors.toList());
        }
    }

    @PostMapping(value = "/set/regions")
    public ResponseEntity<?> setRegions(@RequestBody SetRegionRequest request) {
        wattTimeUtil.setRegions(request.getRegions());
        return ResponseEntity.ok("Regions: " + request.getRegions().toString() + " set");
    }

    @PostMapping(value = "/calculate/force/region/scale")
    public ResponseEntity<?> calculateForceRegionScale() {
        Co2ResultDto minValue = wattTimeUtil.getRegionWithLowestCarbonFootprint();
        log.info("Pushing to topic: {} value: {}", "keda-topic-" + minValue.getMeta().getRegion(), minValue.getData().get(0).getValue());
        pubSubPublisher.publishMessage("keda-topic-" + minValue.getMeta().getRegion(), String.valueOf(minValue.getData().get(0).getValue()));
        return ResponseEntity.ok(minValue.getMeta().getRegion() + " scaled up");
    }

    @PostMapping(value = "/force/region/scale")
    public ResponseEntity<?> forceRegionScale(@RequestBody ForceRegionScaleRequest request) {
        log.info("Pushing to topic: {} value: {}", "keda-topic-" + request.getRegion(), "force");
        pubSubPublisher.publishMessage("keda-topic-" + request.getRegion(), "force");
        return ResponseEntity.ok(request.getRegion() + " scaled up");
    }

    @GetMapping(value = "/v1/cloud/providers")
    public CloudProvidersResponseDto getCloudProviders() {
        return cloudProviderService.getCloudProviders();
    }

    @GetMapping(value = "/v1/cloud/deployments")
    public DeploymentsResponseDto deployments() {
        return cloudProviderService.deployments();
    }

    @ExceptionHandler({WatttimeException.class})
    public ResponseEntity<Object> handleException(Exception ex) {
        return new ResponseEntity<>(
                ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
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
