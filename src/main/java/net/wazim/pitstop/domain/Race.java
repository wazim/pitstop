package net.wazim.pitstop.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Race {

    @JsonProperty
    private final int season;
    @JsonProperty
    private final int round;
    @JsonProperty
    private final String url;
    @JsonProperty
    private final String raceName;
    @JsonProperty
    private final Circuit circuit;
    @JsonProperty
    private final LocalDateTime dateTime;

    public Race(int season, int round, String url, String raceName, Circuit circuit, LocalDateTime dateTime) {
        this.season = season;
        this.round = round;
        this.url = url;
        this.raceName = raceName;
        this.circuit = circuit;
        this.dateTime = dateTime;
    }

    public int season() {
        return season;
    }

    public int round() {
        return round;
    }

    public String url() {
        return url;
    }

    public String raceName() {
        return raceName;
    }

    public Circuit circuit() {
        return circuit;
    }

    public LocalDateTime dateTime() {
        return dateTime;
    }
}
