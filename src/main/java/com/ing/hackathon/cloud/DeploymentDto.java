package com.ing.hackathon.cloud;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeploymentDto {

    private String id;
    private String cloudProviderId;
    private String cloudProviderDestinationId;
    private String regionId;
    private String name;
    private String status;
}
