package net.wazim.pitstop.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Circuit {
    @JsonProperty
    private final String circuitId;
    @JsonProperty
    private final String url;
    @JsonProperty
    private final String circuitName;
    @JsonProperty
    private final Location location;

    public Circuit(String circuitId, String url, String circuitName, Location location) {
        this.circuitId = circuitId;
        this.url = url;
        this.circuitName = circuitName;
        this.location = location;
    }

    public String circuitId() {
        return circuitId;
    }

    public String url() {
        return url;
    }

    public String circuitName() {
        return circuitName;
    }

    public Location location() {
        return location;
    }
}
