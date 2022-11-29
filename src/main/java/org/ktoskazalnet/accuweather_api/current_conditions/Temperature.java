package org.ktoskazalnet.accuweather_api.current_conditions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Temperature {
    @JsonProperty("Metric")
    public Metric metric;
    @JsonProperty("Imperial")
    public Imperial imperial;

    @Override
    public String toString() {
        return "Temperature{" +
                "metric=" + metric +
                ", imperial=" + imperial +
                '}';
    }
}
