package net.wazim.pitstop.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Driver implements Serializable {
    @JsonProperty
    private final String driverId;
    @JsonProperty
    private final int permanentNumber;
    @JsonProperty
    private final String code;
    @JsonProperty
    private final String url;
    @JsonProperty
    private final String givenName;
    @JsonProperty
    private final String familyName;
    @JsonProperty
    private final String nationality;

    public static final Driver LEWIS_HAMILTON = new Driver("hamilton", 44, "HAM", "http://en.wikipedia.org/wiki/Lewis_Hamilton", "Lewis", "Hamilton", "British");
    public static final Driver NICO_ROSBERG = new Driver("rosberg", 6, "ROS", "http://en.wikipedia.org/wiki/Nico_Rosberg", "Nico", "Rosberg", "German");
    public static final Driver FERNANDO_ALONSO = new Driver("alonso", 14, "ALO", "http://en.wikipedia.org/wiki/Fernando_Alonso", "Fernando", "Alonso", "Spanish");

    public Driver(String driverId, int permanentNumber, String code, String url, String givenName, String familyName, String nationality) {
        this.driverId = driverId;
        this.permanentNumber = permanentNumber;
        this.code = code;
        this.url = url;
        this.givenName = givenName;
        this.familyName = familyName;
        this.nationality = nationality;
    }

    public String driverId() {
        return driverId;
    }

    public int permanentNumber() {
        return permanentNumber;
    }

    public String code() {
        return code;
    }

    public String url() {
        return url;
    }

    public String givenName() {
        return givenName;
    }

    public String familyName() {
        return familyName;
    }

    public String nationality() {
        return nationality;
    }
}
