package net.wazim.pitstop.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
    @JsonProperty
    private final int number;
    @JsonProperty
    private final int position;
    @JsonProperty
    private final String positionText;
    @JsonProperty
    private final int points;
    @JsonProperty
    private final Driver driver;
    @JsonProperty
    private final Constructor constructor;
    @JsonProperty
    private final int grid;
    @JsonProperty
    private final int laps;
    @JsonProperty
    private final String status;

    public Result(
            int number,
            int position,
            String positionText,
            int points,
            Driver driver,
            Constructor constructor,
            int grid,
            int laps,
            String status
    ) {
        this.number = number;
        this.position = position;
        this.positionText = positionText;
        this.points = points;
        this.driver = driver;
        this.constructor = constructor;
        this.grid = grid;
        this.laps = laps;
        this.status = status;
    }

    public int number() {
        return number;
    }

    public int position() {
        return position;
    }

    public String positionText() {
        return positionText;
    }

    public int points() {
        return points;
    }

    public Driver driver() {
        return driver;
    }

    public Constructor constructor() {
        return constructor;
    }

    public int grid() {
        return grid;
    }

    public int laps() {
        return laps;
    }

    public String status() {
        return status;
    }
}
