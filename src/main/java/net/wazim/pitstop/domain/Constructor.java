package net.wazim.pitstop.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Constructor {
    @JsonProperty
    private final String constructorId;
    @JsonProperty
    private final String url;
    @JsonProperty
    private final String name;
    @JsonProperty
    private final String nationality;

    public static final Constructor MERCEDES = new Constructor("mercedes", "http://wikipedia.org/Mercedes", "Mercedes", "German");

    public Constructor(String constructorId, String url, String name, String nationality) {
        this.constructorId = constructorId;
        this.url = url;
        this.name = name;
        this.nationality = nationality;
    }

    public String constructorId() {
        return constructorId;
    }

    public String url() {
        return url;
    }

    public String name() {
        return name;
    }

    public String nationality() {
        return nationality;
    }
}
