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

    public static class Data {
        String point_time;
        Float value;
    }

}
