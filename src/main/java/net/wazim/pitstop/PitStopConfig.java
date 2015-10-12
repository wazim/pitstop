package net.wazim.pitstop;

import net.wazim.pitstop.controller.DriverController;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@SuppressWarnings("unused")
public class PitStopConfig {

    @Bean
    public DriverController driverController() {
        return new DriverController();
    }

}
