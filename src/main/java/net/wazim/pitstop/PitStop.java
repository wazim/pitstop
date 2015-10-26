package net.wazim.pitstop;

import net.wazim.pitstop.domain.Driver;
import net.wazim.pitstop.domain.Race;
import net.wazim.pitstop.domain.RaceResult;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class PitStop {

    private ConfigurableApplicationContext applicationContext;
    private RestTemplate client = new RestTemplate();

    public void start() {
        applicationContext = new SpringApplicationBuilder(PitStopConfig.class)
                .showBanner(false)
                .run();
    }

    public void stop() {
        applicationContext.stop();
        applicationContext.close();
    }

    public void primeDrivers(List<Driver> drivers) {
        client.postForEntity("http://localhost:8080/drivers", drivers, String.class);
    }

    public void primeRaces(List<Race> races) {
        client.postForEntity("http://localhost:8080/races", races, String.class);
    }

    public void primeRaceResults(List<RaceResult> raceResults) {
        client.postForEntity("http://localhost:8080/raceResults", raceResults, String.class);
    }
}
