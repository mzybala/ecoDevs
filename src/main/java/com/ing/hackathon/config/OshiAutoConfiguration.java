package com.ing.hackathon.config;

import com.ing.hackathon.sci.EnergyConsumptionProvider;
import com.ing.hackathon.sci.PowerUtilizationEnergyConsumptionProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import oshi.hardware.HardwareAbstractionLayer;

@ConditionalOnClass({ HardwareAbstractionLayer.class })
@ConditionalOnMissingBean({ EnergyConsumptionProvider.class })
public class OshiAutoConfiguration {

    @Bean
    public EnergyConsumptionProvider oshiHardwarePowerUtilizationEnergyConsumptionProvider() {
        return new PowerUtilizationEnergyConsumptionProvider();
    }

}
