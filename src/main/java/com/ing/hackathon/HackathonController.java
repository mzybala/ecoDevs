package com.ing.hackathon;

import com.ing.hackathon.cloud.PubSubPublisher;
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

    @ExceptionHandler({WatttimeException.class})
    public ResponseEntity<Object> handleException(Exception ex) {
        return new ResponseEntity<>(
                ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
