package com.ing.hackathon.cloud;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CloudProviderDto {

    private String id;
    private String cloudName;
    private String continet;
    private String country;
    private String city;
    private LocationDto location;
}
