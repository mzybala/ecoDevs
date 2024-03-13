package com.ing.hackathon.watttime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Co2ResultDto implements Serializable {

    @JsonProperty("token")
    String token;

    @JsonProperty("data")
    List<Data> data;

    @JsonProperty("meta")
    Meta meta;

    public static class Data {
        @JsonProperty("point_time")
        String pointTime;
        @JsonProperty("value")
        Float value;
    }

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
