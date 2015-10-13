package net.wazim.pitstop.controller;

import net.wazim.pitstop.PitStop;
import net.wazim.pitstop.domain.Driver;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class DriverControllerTest {

    private static PitStop pitStop;

    @BeforeClass
    public static void startPitstop() {
        pitStop = new PitStop();
        pitStop.start();
    }

    @AfterClass
    public static void stopPitstop() {
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
        ResponseEntity<String> driversResponse = client.getForEntity("http://localhost:8080/f1/2015/drivers.json", String.class);

        assertThat(driversResponse.getBody(), containsString("PEDR"));
        assertThat(driversResponse.getBody(), containsString("0"));
        assertThat(driversResponse.getBody(), containsString("PED"));
        assertThat(driversResponse.getBody(), containsString("http://pedro.com"));
        assertThat(driversResponse.getBody(), containsString("Pedr"));
        assertThat(driversResponse.getBody(), containsString("Oh"));
        assertThat(driversResponse.getBody(), containsString("English"));
    }

    @Test
    public void getADriverByFamilyName() {
        pitStop.primeDrivers(Collections.singletonList(new Driver(
                "TEST",
                14,
                "TEST",
                "http://test.com",
                "Test",
                "Match",
                "French"
        )));

        TestRestTemplate client = new TestRestTemplate();
        ResponseEntity<String> driversResponse = client.getForEntity("http://localhost:8080/f1/2015/drivers/match.json", String.class);

        assertThat(driversResponse.getBody(), containsString("TEST"));
        assertThat(driversResponse.getBody(), containsString("14"));
        assertThat(driversResponse.getBody(), containsString("TEST"));
        assertThat(driversResponse.getBody(), containsString("http://test.com"));
        assertThat(driversResponse.getBody(), containsString("Test"));
        assertThat(driversResponse.getBody(), containsString("Match"));
        assertThat(driversResponse.getBody(), containsString("French"));
    }
}
