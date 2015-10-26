package net.wazim.pitstop.controller;

import net.wazim.pitstop.PitStop;
import net.wazim.pitstop.domain.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;

import static java.util.Arrays.asList;
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
        pitStop.primeRaceResults(Collections.singletonList(
                new RaceResult(
                        aRace(),
                        asList(
                                new Result(
                                        6,
                                        1,
                                        "1",
                                        25,
                                        new Driver(
                                                "rosberg",
                                                6,
                                                "ROS",
                                                "http://rosberg.com",
                                                "Niko",
                                                "Rosberg",
                                                "German"
                                        ),
                                        new Constructor(
                                                "mercedes",
                                                "http://mercedes.com",
                                                "Mercedes",
                                                "German"
                                        ),
                                        1,
                                        66,
                                        "Finished"
                                ),
                                new Result(
                                        1,
                                        2,
                                        "2",
                                        20,
                                        new Driver(
                                                "hamilton",
                                                1,
                                                "HAM",
                                                "http://HAMILTON.com",
                                                "Lewis",
                                                "Hamilton",
                                                "British"
                                        ),
                                        new Constructor(
                                                "mercedes",
                                                "http://mercedes.com",
                                                "Mercedes",
                                                "German"
                                        ),
                                        2,
                                        66,
                                        "Finished"
                                )
                        )

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

    private Race aRace() {
        return new Race(
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
                ),
                LocalDateTime.of(LocalDate.of(2015, 10, 10), LocalTime.of(12, 12, 12))
        );
    }
}
