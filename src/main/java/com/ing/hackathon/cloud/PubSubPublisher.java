package com.ing.hackathon.cloud;

public interface PubSubPublisher {
    void publishMessage(String topicName, String message);
}
