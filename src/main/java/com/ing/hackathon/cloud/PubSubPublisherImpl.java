package com.ing.hackathon.cloud;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;

public class PubSubPublisherImpl implements PubSubPublisher {

    private final PubSubTemplate pubSubTemplate;

    public PubSubPublisherImpl(PubSubTemplate pubSubTemplate) {
        this.pubSubTemplate = pubSubTemplate;
    }

    @Override
    public void publishMessage(String topicName, String message) {
        pubSubTemplate.publish(topicName, message);
    }
}
