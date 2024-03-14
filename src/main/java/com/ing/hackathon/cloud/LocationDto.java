package com.ing.hackathon.cloud;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LocationDto {
    private Double latitude;
    private Double longtitude;
}
