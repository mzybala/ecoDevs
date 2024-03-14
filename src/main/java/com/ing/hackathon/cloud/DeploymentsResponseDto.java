package com.ing.hackathon.cloud;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DeploymentsResponseDto {

    List<DeploymentDto> deployments = new ArrayList<>();
}
