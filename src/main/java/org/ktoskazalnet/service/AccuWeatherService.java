package org.ktoskazalnet.service;

import org.ktoskazalnet.accuweather_api.current_conditions.Root;
import org.ktoskazalnet.accuweather_api.exception.CityNotFoundException;
import org.ktoskazalnet.accuweather_api.locations.City;
import org.ktoskazalnet.accuweather_api.locations.TopCityCount;
import org.ktoskazalnet.client.AccuWeatherClient;

import java.util.List;

public class AccuWeatherService {
    private final AccuWeatherClient accuWeatherClient;

    public AccuWeatherService(AccuWeatherClient accuWeatherClient) {
        this.accuWeatherClient = accuWeatherClient;
    }

    public List<City> getCitiesList(TopCityCount topCityCount) {
        return accuWeatherClient.getTopCities(topCityCount);
    }

    public List<Root> checkWeather(String cityName) {
        return accuWeatherClient.getCurrentCondition(cityName);
    }
}
