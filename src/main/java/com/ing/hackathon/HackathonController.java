package com.ing.hackathon;

import com.ing.hackathon.watttime.Co2ResultDto;
import com.ing.hackathon.watttime.WattTimeClient;
import lombok.AllArgsConstructor;
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
}
