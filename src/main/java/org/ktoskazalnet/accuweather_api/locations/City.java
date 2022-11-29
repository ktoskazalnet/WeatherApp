package org.ktoskazalnet.accuweather_api.locations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class City {
    @JsonProperty("Key")
    private String key;
    @JsonProperty("EnglishName")
    private String englishName;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    @Override
    public String toString() {
        return "City{" +
                "key='" + key + '\'' +
                ", englishName='" + englishName + '\'' +
                '}';
    }
}
