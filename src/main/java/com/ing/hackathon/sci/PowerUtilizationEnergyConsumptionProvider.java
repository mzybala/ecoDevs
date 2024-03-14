package com.ing.hackathon.sci;

import lombok.extern.slf4j.Slf4j;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;

@Slf4j
public class PowerUtilizationEnergyConsumptionProvider implements EnergyConsumptionProvider {
    /**
     * This calculates the power usage in kW.
     * While charging the power usage rate is positive otherwise it negative so its absolute value is considered.
     * It gives the reading in mW, hence divided it by 10e-6
     *
     * @return the energy consumption
     */
    @Override
    public double getEnergyConsumption() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        log.info(hal.getPowerSources()
                .stream()
                .mapToDouble(ps -> Math.abs(ps.getPowerUsageRate()) / 1000000)
                .average().toString());

        return hal.getPowerSources()
                .stream()
                .mapToDouble(ps -> Math.abs(ps.getPowerUsageRate()) / 1000000)
                .average()
                .orElse(0.0);
    }
}
