package net.wazim.pitstop.controller;

import net.wazim.pitstop.PitStop;
import net.wazim.pitstop.builder.RaceBuilder;
import net.wazim.pitstop.builder.ResultBuilder;
import net.wazim.pitstop.domain.Circuit;
import net.wazim.pitstop.domain.Location;
import net.wazim.pitstop.domain.Race;
import net.wazim.pitstop.domain.RaceResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static java.util.Arrays.asList;
import static net.wazim.pitstop.domain.Driver.LEWIS_HAMILTON;
import static net.wazim.pitstop.domain.Driver.NICO_ROSBERG;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class RaceResultsControllerTest {

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
    public void raceResultsTest() {
        pitStop.primeRaceResult(
                new RaceResult(
                        new RaceBuilder().build(),
                        asList(
                                new ResultBuilder().withDriver(NICO_ROSBERG).build(),
                                new ResultBuilder().withDriver(LEWIS_HAMILTON).build()
                        )

                )
        );

        TestRestTemplate client = new TestRestTemplate();
        ResponseEntity<String> driversResponse = client.getForEntity("http://localhost:8080/f1/2015/1/results.json", String.class);

        assertThat(driversResponse.getBody(), containsString("6"));
        assertThat(driversResponse.getBody(), containsString("1"));
        assertThat(driversResponse.getBody(), containsString("Rosberg"));
        assertThat(driversResponse.getBody(), containsString("5"));
        assertThat(driversResponse.getBody(), containsString("2"));
        assertThat(driversResponse.getBody(), containsString("Hamilton"));
    }

}
