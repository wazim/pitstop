package net.wazim.pitstop.controller;

import net.wazim.pitstop.PitStop;
import net.wazim.pitstop.domain.Circuit;
import net.wazim.pitstop.domain.Location;
import net.wazim.pitstop.domain.Race;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class RaceScheduleControllerTest {

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
    public void primeRaceScheduleAndRetrieveIt() {
        pitStop.primeRaces(Collections.singletonList(new Race(
                2015,
                1,
                "http://wikipedia.org",
                "Australian Grand Prix",
                new Circuit(
                        "albert_park",
                        "http://albert-park.com",
                        "Albert Park Grand Prix Circuit",
                        new Location(
                                "-37.8497",
                                "144.968",
                                "Melbourne",
                                "Australia"
                        )
                )
        )));

        TestRestTemplate client = new TestRestTemplate();
        ResponseEntity<String> racesResponse = client.getForEntity("http://localhost:8080/f1/current.json", String.class);

        assertThat(racesResponse.getBody(), containsString("2015"));
        assertThat(racesResponse.getBody(), containsString("1"));
        assertThat(racesResponse.getBody(), containsString("http://wikipedia.org"));
        assertThat(racesResponse.getBody(), containsString("albert_park"));
        assertThat(racesResponse.getBody(), containsString("http://albert-park.com"));
        assertThat(racesResponse.getBody(), containsString("Albert Park Grand Prix Circuit"));
        assertThat(racesResponse.getBody(), containsString("-37.8497"));
        assertThat(racesResponse.getBody(), containsString("144.968"));
        assertThat(racesResponse.getBody(), containsString("Melbourne"));
        assertThat(racesResponse.getBody(), containsString("Australia"));
    }

    @Test
    public void primeRaceScheduleAndRetrieveRaceByRound() {
        pitStop.primeRaces(Collections.singletonList(new Race(
                2015,
                5,
                "http://wikipedia.org",
                "British Grand Prix",
                new Circuit(
                        "albert_park",
                        "http://albert-park.com",
                        "Albert Park Grand Prix Circuit",
                        new Location(
                                "-37.8497",
                                "144.968",
                                "Melbourne",
                                "British"
                        )
                )
        )));

        TestRestTemplate client = new TestRestTemplate();
        ResponseEntity<String> racesResponse = client.getForEntity("http://localhost:8080/f1/current/5.json", String.class);

        assertThat(racesResponse.getBody(), containsString("2015"));
        assertThat(racesResponse.getBody(), containsString("5"));
        assertThat(racesResponse.getBody(), containsString("http://wikipedia.org"));
        assertThat(racesResponse.getBody(), containsString("albert_park"));
        assertThat(racesResponse.getBody(), containsString("http://albert-park.com"));
        assertThat(racesResponse.getBody(), containsString("Albert Park Grand Prix Circuit"));
        assertThat(racesResponse.getBody(), containsString("-37.8497"));
        assertThat(racesResponse.getBody(), containsString("144.968"));
        assertThat(racesResponse.getBody(), containsString("Melbourne"));
        assertThat(racesResponse.getBody(), containsString("British"));
    }

}
