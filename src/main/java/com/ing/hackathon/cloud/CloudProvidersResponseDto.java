package com.ing.hackathon.cloud;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CloudProvidersResponseDto {
    List<CloudProviderDto> cloudProviders = new ArrayList<>();
}
