package net.wazim.pitstop.controller;

import net.wazim.pitstop.PitStop;
import net.wazim.pitstop.domain.Driver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class DriverControllerTest {

    private PitStop pitStop;

    @Before
    public void startPitstop() {
        pitStop = new PitStop();
        pitStop.start();
    }

    @After
    public void stopPitstop() {
        pitStop.stop();
    }

    @Test
    public void driverControllerTest() {
        pitStop.primeDrivers(Collections.singletonList(new Driver(
                "PEDR",
                0,
                "PED",
                "http://pedro.com",
                "Pedr",
                "Oh",
                "English"
        )));

        TestRestTemplate client = new TestRestTemplate();
        ResponseEntity<String> driversResponse = client.getForEntity("http://localhost:8080/drivers", String.class);

        assertThat(driversResponse.getBody(), containsString("PEDR"));
        assertThat(driversResponse.getBody(), containsString("0"));
        assertThat(driversResponse.getBody(), containsString("PED"));
        assertThat(driversResponse.getBody(), containsString("http://pedro.com"));
        assertThat(driversResponse.getBody(), containsString("Pedr"));
        assertThat(driversResponse.getBody(), containsString("Oh"));
        assertThat(driversResponse.getBody(), containsString("English"));
    }
}
