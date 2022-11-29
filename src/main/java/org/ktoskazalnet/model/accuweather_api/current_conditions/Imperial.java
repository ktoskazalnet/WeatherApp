package org.ktoskazalnet.model.accuweather_api.current_conditions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Imperial {
    @JsonProperty("Value")
    public int value;
    @JsonProperty("Unit")
    public String unit;
    @JsonProperty("UnitType")
    public int unitType;

    @Override
    public String toString() {
        return "Imperial{" +
                "value=" + value +
                ", unit='" + unit + '\'' +
                ", unitType=" + unitType +
                '}';
    }
}
