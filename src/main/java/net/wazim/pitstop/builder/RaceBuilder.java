package net.wazim.pitstop.builder;

import net.wazim.pitstop.domain.Circuit;
import net.wazim.pitstop.domain.Location;
import net.wazim.pitstop.domain.Race;

import java.time.LocalDateTime;

public class RaceBuilder {

    private int season;
    private int round;
    private String url;
    private String raceName;
    private Circuit circuit;
    private LocalDateTime dateTime;

    public RaceBuilder() {
        this.season = 1;
        this.round = 1;
        this.url = "http://wikipedia.org";
        this.raceName = "Silverstone";
        this.circuit = new Circuit("1", "http://wikipedia.org", "Silverstone", new Location("41.123", "-2.123", "England", "United Kingdom"));
        this.dateTime = LocalDateTime.now();
    }

    public RaceBuilder withSeason(int season) {
        this.season = season;
        return this;
    }

    public RaceBuilder withRound(int round) {
        this.round = round;
        return this;
    }

    public RaceBuilder withRaceName(String raceName){
        this.raceName = raceName;
        return this;
    }

    public RaceBuilder withCircuit(Circuit circuit) {
        this.circuit = circuit;
        return this;
    }

    public RaceBuilder withDateTime(LocalDateTime localDateTime) {
        this.dateTime = localDateTime;
        return this;
    }

    public Race build() {
        return new Race(
                season,
                round,
                url,
                raceName,
                circuit,
                dateTime
        );
    }
}
