package com.ing.hackathon.cloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Scheduler {

    @Autowired
    PubSubPublisher pubSubPublisher;

    @Scheduled(fixedRate = 5000)
    public void schedule() {
        log.info("Pushing to topic");
        pubSubPublisher.publishMessage("keda-topic", "1");
    }
}
