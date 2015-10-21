package net.wazim.pitstop.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {
    @JsonProperty
    private final String latitude;
    @JsonProperty
    private final String longitude;
    @JsonProperty
    private final String locality;
    @JsonProperty
    private final String country;

    public Location(String latitude, String longitude, String locality, String country) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.locality = locality;
        this.country = country;
    }

    public String latitude() {
        return latitude;
    }

    public String longitude() {
        return longitude;
    }

    public String locality() {
        return locality;
    }

    public String country() {
        return country;
    }
}
