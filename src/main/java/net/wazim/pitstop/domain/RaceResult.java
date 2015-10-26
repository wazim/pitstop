package net.wazim.pitstop.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RaceResult {

    @JsonProperty
    private final Race race;
    @JsonProperty
    private final List<Result> results;

    public RaceResult(Race race, List<Result> results) {
        this.race = race;
        this.results = results;
    }

    public Race race() {
        return race;
    }

    public List<Result> results() {
        return results;
    }
}
