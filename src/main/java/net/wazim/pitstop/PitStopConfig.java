package net.wazim.pitstop;

import net.wazim.pitstop.controller.DriverController;
import net.wazim.pitstop.controller.RaceResultsController;
import net.wazim.pitstop.controller.RaceScheduleController;
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

    @Bean
    public RaceScheduleController raceScheduleController() {
        return new RaceScheduleController();
    }

    @Bean
    public RaceResultsController raceResultsController() {
        return new RaceResultsController();
    }

}
