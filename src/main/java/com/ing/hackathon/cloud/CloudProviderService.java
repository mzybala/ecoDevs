package com.ing.hackathon.cloud;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CloudProviderService {

    private static final String GCP_NAME = "Google Cloud Platform";

    public CloudProvidersResponseDto getCloudProviders() {
        CloudProvidersResponseDto response = new CloudProvidersResponseDto();

        CloudProviderDto pl = CloudProviderDto.builder()
            .id("pl")
            .cloudName(GCP_NAME)
            .continet("Europe")
            .country("Poland")
            .city("europe-central2")
            .location(LocationDto.builder().latitude(52.10606).longtitude(20.958876).build())
            .build();

        CloudProviderDto nl = CloudProviderDto.builder()
                .id("nl")
                .cloudName(GCP_NAME)
                .continet("Europe")
                .country("Netherlands")
                .city("europe-west4")
                .location(LocationDto.builder().latitude(51.98762).longtitude(4.429028).build())
                .build();

        CloudProviderDto fi = CloudProviderDto.builder()
                .id("fi")
                .cloudName(GCP_NAME)
                .continet("Europe")
                .country("Finland")
                .city("europe-west4")
                .location(LocationDto.builder().latitude(63.791977).longtitude(26.16085).build())
                .build();

        response.getCloudProviders().addAll(List.of(pl, nl, fi));
        return response;
    }


    public DeploymentsResponseDto deployments() {
        final DeploymentsResponseDto response = new DeploymentsResponseDto();

        final DeploymentDto d1 = DeploymentDto.builder()
                .id("c335d48c-812f-4a1b-8a32-c3693e14ad66")
                .cloudProviderId("pl")
                .regionId("55fcecf8-472b-4aa7-8749-cd6000a8871f")
                .name("NGINX")
                .status("Running")
                .build();

        final DeploymentDto d2 = DeploymentDto.builder()
                .id("a6c37ecb-1d91-4c76-91db-66586296e972")
                .cloudProviderId("pl")
                .cloudProviderDestinationId("55fcecf8-472b-4aa7-8749-cd6000a8871f")
                .regionId("55fcecf8-472b-4aa7-8749-cd6000a8871f")
                .name("IT Tools")
                .status("Migrating")
                .build();

        final DeploymentDto d3 = DeploymentDto.builder()
                .id("7df140e5-cf5a-4ca8-8600-5b7a69d95dec")
                .cloudProviderId("pl")
                .regionId("55fcecf8-472b-4aa7-8749-cd6000a8871f")
                .name("Homepage")
                .status("Stopped")
                .build();
        response.getDeployments().addAll(List.of(d1, d2, d3));
        return response;
    }
}