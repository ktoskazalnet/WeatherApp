package org.ktoskazalnet.model.accuweather_api.current_conditions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentConditionRoot {
    @JsonProperty("LocalObservationDateTime")
    public Date localObservationDateTime;
    @JsonProperty("EpochTime")
    public int epochTime;
    @JsonProperty("WeatherText")
    public String weatherText;
    @JsonProperty("WeatherIcon")
    public int weatherIcon;
    @JsonProperty("HasPrecipitation")
    public boolean hasPrecipitation;
    @JsonProperty("PrecipitationType")
    public Object precipitationType;
    @JsonProperty("IsDayTime")
    public boolean isDayTime;
    @JsonProperty("Temperature")
    public Temperature temperature;
    @JsonProperty("MobileLink")
    public String mobileLink;
    @JsonProperty("Link")
    public String link;

    @Override
    public String toString() {
        return "Root{" +
                "localObservationDateTime=" + localObservationDateTime +
                ", epochTime=" + epochTime +
                ", weatherText='" + weatherText + '\'' +
                ", weatherIcon=" + weatherIcon +
                ", hasPrecipitation=" + hasPrecipitation +
                ", precipitationType=" + precipitationType +
                ", isDayTime=" + isDayTime +
                ", temperature=" + temperature +
                ", mobileLink='" + mobileLink + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
