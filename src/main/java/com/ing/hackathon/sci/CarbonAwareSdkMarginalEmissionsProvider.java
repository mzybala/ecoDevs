package com.ing.hackathon.sci;

import com.ing.hackathon.apis.CarbonAwareSdkClient;
import com.ing.hackathon.models.Emissions;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;

@Slf4j
public class CarbonAwareSdkMarginalEmissionsProvider implements MarginalEmissionsProvider {

    private CarbonAwareSdkClient client;

    public CarbonAwareSdkMarginalEmissionsProvider(CarbonAwareSdkClient client) {
        this.client = client;
    }

    @Override
    public double getMarginalEmissions() {
        //List<Emissions> emissions = client.emissionsForLocation();
        Emissions e1 = new Emissions();
        e1.setDuration("00:00:01");
        e1.setLocation("westus");
        e1.setRating(25.0);
        List<Emissions> emissions = List.of(e1);
        // sort the emissions by time, and then filter
        final long now = System.currentTimeMillis();
        Emissions closestEmissionsData = emissions.stream().min(new Comparator<Emissions>() {
            public int compare(Emissions e1, Emissions e2) {
                long diff1 = Math.abs(e1.getTime().toInstant().toEpochMilli() - now);
                long diff2 = Math.abs(e2.getTime().toInstant().toEpochMilli() - now);
                return Long.compare(diff1, diff2);
            }
        }).orElse(null);

        if (closestEmissionsData == null) {
            log.error("There was a problem getting emissions data from Carbon Aware SDK");
            return 0.0;
        }

        return closestEmissionsData.getRating();
    }
}