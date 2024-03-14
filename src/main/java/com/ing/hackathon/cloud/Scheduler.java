package com.ing.hackathon.cloud;

import com.ing.hackathon.watttime.Co2ResultDto;
import com.ing.hackathon.watttime.WattTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Scheduler {

    @Autowired
    PubSubPublisher pubSubPublisher;

    @Autowired
    WattTimeUtil wattTimeUtil;

    @Scheduled(fixedRate = 5000)
    public void scheduleCarbonFootprintIndex() {
        Co2ResultDto minValue = wattTimeUtil.getRegionWithLowestCarbonFootprint();
        log.info("Pushing to topic: {} value: {}", "keda-topic-" + minValue.getMeta().getRegion(), minValue.getData().get(0).getValue());
        pubSubPublisher.publishMessage("keda-topic-" + minValue.getMeta().getRegion(), String.valueOf(minValue.getData().get(0).getValue()));
    }
}
