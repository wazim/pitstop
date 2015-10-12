package net.wazim.pitstop;

import net.wazim.pitstop.domain.Driver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class PitStop {

    private ConfigurableApplicationContext applicationContext;
    private RestTemplate client = new RestTemplate();

    public void start() {
        applicationContext = new SpringApplicationBuilder(PitStopConfig.class)
                .showBanner(true)
                .run();
    }

    public void stop() {
        applicationContext.stop();
    }

    public void primeDrivers(List<Driver> drivers) {
        client.postForEntity("http://localhost:8080/drivers", drivers, String.class);
    }
}
