package com.ing.hackathon.watttime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Co2ResultDto {

    @JsonProperty("token")
    String token;

    @JsonProperty("data")
    List<Data> data;

    @JsonProperty("meta")
    Meta meta;

    @Setter
    @Getter
    public static class Data {
        @JsonProperty("point_time")
        String pointTime;
        @JsonProperty("value")
        Float value;
    }

    @Setter
    @Getter
    public static class Meta {
        @JsonProperty("data_point_period_seconds")
        String dataPointPeriodSeconds;
        @JsonProperty("region")
        String region;
        @JsonProperty("signal_type")
        String signalType;
        @JsonProperty("units")
        String units;
        @JsonProperty("warnings")
        List<String> warnings;
    }

}
