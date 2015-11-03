package net.wazim.pitstop.controller;

import net.wazim.pitstop.PitStop;
import net.wazim.pitstop.domain.Driver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static net.wazim.pitstop.domain.Driver.FERNANDO_ALONSO;
import static net.wazim.pitstop.domain.Driver.LEWIS_HAMILTON;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class DriverControllerTest {

    private static PitStop pitStop;

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
        pitStop.primeDriver(LEWIS_HAMILTON);

        TestRestTemplate client = new TestRestTemplate();
        ResponseEntity<String> driversResponse = client.getForEntity("http://localhost:8080/f1/2015/drivers.json", String.class);

        assertThat(driversResponse.getBody(), containsString("hamilton"));
        assertThat(driversResponse.getBody(), containsString("44"));
        assertThat(driversResponse.getBody(), containsString("HAM"));
        assertThat(driversResponse.getBody(), containsString("Lewis"));
        assertThat(driversResponse.getBody(), containsString("Hamilton"));
        assertThat(driversResponse.getBody(), containsString("British"));
    }

    @Test
    public void getADriverByFamilyName() {
        pitStop.primeDriver(FERNANDO_ALONSO);

        TestRestTemplate client = new TestRestTemplate();
        ResponseEntity<String> driversResponse = client.getForEntity("http://localhost:8080/f1/2015/drivers/alonso.json", String.class);

        assertThat(driversResponse.getBody(), containsString("alonso"));
        assertThat(driversResponse.getBody(), containsString("14"));
        assertThat(driversResponse.getBody(), containsString("ALO"));
        assertThat(driversResponse.getBody(), containsString("Fernando"));
        assertThat(driversResponse.getBody(), containsString("Alonso"));
        assertThat(driversResponse.getBody(), containsString("Spanish"));
    }
}
