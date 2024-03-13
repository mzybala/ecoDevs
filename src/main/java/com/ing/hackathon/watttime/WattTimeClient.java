package com.ing.hackathon.watttime;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class WattTimeClient {

    private static final String BASE_URL = "https://api.watttime.org";
    private static final String REGISTER_URL = BASE_URL + "/register";
    private static final String LOGIN_URL = BASE_URL + "/login";
    private static final String FORECAST = BASE_URL + "/v3/forecast";
    private static final String SIGNAL_INDEX = BASE_URL + "/v3/signal-index";
    private static final String HISTORICAL = BASE_URL + "/v3/historical";
    private static final String GET_REGION = BASE_URL + "/v3/region-from-loc";
    private static final String LOGIN = "ecoDevsTest2";
    private static final String PASSWORD = "Hackathon123!";
    private static final String MAIL = "skt17700@zslsz.com";

    RestTemplate restTemplate = new RestTemplate();

    public void getRegion(final String latitude, final String longitude) throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.put(HttpHeaders.AUTHORIZATION, Collections.singletonList("Bearer " + getToken()));
        HttpEntity<?> entity = new HttpEntity<>(headers);
        URI uri = new URI(GET_REGION + "?latitude=" + latitude + "&longitude=" + longitude +
                "&signal_type=" + "co2_moer");

        HttpEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        System.out.println(response);
    }

    public Co2ResultDto getCurrentHistorical(final String region) throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.put(HttpHeaders.AUTHORIZATION, Collections.singletonList("Bearer " + getToken()));
        HttpEntity<?> entity = new HttpEntity<>(headers);
        Instant now = Instant.now();
        URI uri = new URI(HISTORICAL + "?region=" + region + "&signal_type=" + "co2_moer" +
                "&start=" + now.minus(5, ChronoUnit.MINUTES).toString() +
                "&end=" + now);

        HttpEntity<Co2ResultDto> responseUntity = restTemplate.exchange(uri, HttpMethod.GET, entity, Co2ResultDto.class);
        System.out.println(responseUntity);
        return responseUntity.getBody();
    }

    public Co2ResultDto getSignalIndex(final String region) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.put(HttpHeaders.AUTHORIZATION, Collections.singletonList("Bearer " + getToken()));
            HttpEntity<?> entity = new HttpEntity<>(headers);
            URI uri = new URI(SIGNAL_INDEX + "?region=" + region + "&signal_type=" + "co2_moer");
            HttpEntity<Co2ResultDto> response = restTemplate.exchange(uri, HttpMethod.GET, entity, Co2ResultDto.class);
            System.out.println(response.getBody());
            return response.getBody();
        } catch (Exception e) {
            System.out.println("region: " + region);
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void getForecast(final String region) throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.put(HttpHeaders.AUTHORIZATION, Collections.singletonList("Bearer " + getToken()));
        HttpEntity<?> entity = new HttpEntity<>(headers);
        URI uri = new URI(FORECAST + "?region=" + region + "&signal_type=" + "co2_moer");

        HttpEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        System.out.println(response);
    }

    public String getToken() {
        HttpHeaders headers = new HttpHeaders();
        final String user = LOGIN + ":" + PASSWORD;
        headers.put(HttpHeaders.AUTHORIZATION, Collections.singletonList("Basic " + Base64.getEncoder().encodeToString(user.getBytes())));
        HttpEntity<?> entity = new HttpEntity<>(headers);
        HttpEntity<TokenDto> response = restTemplate.exchange(LOGIN_URL, HttpMethod.GET, entity, TokenDto.class);
        return Objects.requireNonNull(response.getBody()).getToken();
    }


    public void register() {
        HttpHeaders headers = new HttpHeaders();
        Map<String, String> body = new HashMap<>();
        body.put("username", LOGIN);
        body.put("password", PASSWORD);
        body.put("email", MAIL);
        body.put("org", "Ing");

        HttpEntity<?> entity = new HttpEntity<>(body, headers);

        HttpEntity<String> response = restTemplate.exchange(REGISTER_URL, HttpMethod.POST, entity, String.class);
        System.out.println(response);
    }
}
