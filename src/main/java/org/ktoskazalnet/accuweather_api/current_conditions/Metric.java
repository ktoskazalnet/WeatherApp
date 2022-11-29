package org.ktoskazalnet.accuweather_api.current_conditions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Metric {
    @JsonProperty("Value")
    public double value;
    @JsonProperty("Unit")
    public String unit;
    @JsonProperty("UnitType")
    public int unitType;

    @Override
    public String toString() {
        return "Metric{" +
                "value=" + value +
                ", unit='" + unit + '\'' +
                ", unitType=" + unitType +
                '}';
    }
}
