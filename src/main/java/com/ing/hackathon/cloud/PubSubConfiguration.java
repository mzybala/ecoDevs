package com.ing.hackathon.cloud;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PubSubConfiguration {

    @ConditionalOnProperty(value = "spring.cloud.gcp.pubsub.enabled", matchIfMissing = true)
    @Bean
    public PubSubPublisher pubSubPublisherImpl(PubSubTemplate pubSubTemplate) {
        return new PubSubPublisherImpl(pubSubTemplate);
    }

    @ConditionalOnProperty(value = "spring.cloud.gcp.pubsub.enabled", havingValue = "false")
    @Bean
    public PubSubPublisher pubSubPublisherDummy() {
        return new PubSubPublisherDummy();
    }
}
