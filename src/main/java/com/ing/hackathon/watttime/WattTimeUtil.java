package com.ing.hackathon.watttime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WattTimeUtil {
    private static final List<String> REGIONS = List.of("PL", "DE", "FR");

    @Autowired
    WattTimeClient wattTimeClient;

    public Co2ResultDto getRegionWithLowestCarbonFootprint() {
        List<Co2ResultDto> results = REGIONS.stream().map(region -> wattTimeClient.getSignalIndex(region)).collect(Collectors.toList());

        return results.stream()
                .min(Comparator.comparingDouble(result -> result.getData().get(0).getValue())).get();
    }
}
