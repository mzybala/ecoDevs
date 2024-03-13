package com.ing.hackathon.watttime;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;


class WattTimeClientTest {

//    @Test
//    public void registerTest() {
//        WattTimeClient wattTimeClient = new WattTimeClient();
//        wattTimeClient.register();
//    }

    @Test
    public void getTokenTest() {
        WattTimeClient wattTimeClient = new WattTimeClient();
        wattTimeClient.getToken();
    }

    @Test
    public void getForecast() throws URISyntaxException {
        WattTimeClient wattTimeClient = new WattTimeClient();
        wattTimeClient.getForecast("CAISO_NORTH");
    }

    @Test
    public void getSignalIndex() throws URISyntaxException {
        WattTimeClient wattTimeClient = new WattTimeClient();
        wattTimeClient.getSignalIndex("FR");
    }

    @Test
    public void getHistorical() throws URISyntaxException {
        WattTimeClient wattTimeClient = new WattTimeClient();
        wattTimeClient.getCurrentHistorical("CAISO_NORTH");
    }

    @Test
    public void getRegion() throws URISyntaxException {
        WattTimeClient wattTimeClient = new WattTimeClient();
        wattTimeClient.getRegion("52.229", "21.011");
    }
}