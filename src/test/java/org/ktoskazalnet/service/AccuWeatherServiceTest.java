package org.ktoskazalnet.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.ktoskazalnet.model.accuweather_api.current_conditions.CurrentConditionRoot;
import org.ktoskazalnet.model.accuweather_api.locations.City;
import org.ktoskazalnet.model.accuweather_api.locations.TopCityCount;
import org.ktoskazalnet.client.AccuWeatherClient;
import org.ktoskazalnet.repository.CacheCityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccuWeatherServiceTest {

    private final AccuWeatherClient accuWeatherClient = mock(AccuWeatherClient.class);
    private final CacheCityRepository cacheCityRepository = mock(CacheCityRepository.class);
    private final Scanner scanner = mock(Scanner.class);

    private final AccuWeatherService accuWeatherService =
            new AccuWeatherService(accuWeatherClient, cacheCityRepository, scanner);

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(accuWeatherClient);
    }

    @Test
    void getCitiesList_ShouldReturnTopCitiesList() {
        List<City> cities = new ArrayList<>();
        TopCityCount topCityCount = TopCityCount.valueOf(50);

        when(accuWeatherClient.getTopCities(topCityCount)).thenReturn(cities);
        List<City> topCitiesList = accuWeatherClient.getTopCities(topCityCount);

        assertIterableEquals(topCitiesList, cities);
        verify(accuWeatherClient).getTopCities(topCityCount);
    }

    @Test
    void checkWeather_ShouldReturnCurrentCondition() {
        List<CurrentConditionRoot> currentCondition = new ArrayList<>();
        when(accuWeatherClient.getCurrentCondition(anyString())).thenReturn(currentCondition);
        List<CurrentConditionRoot> expected = accuWeatherClient.getCurrentCondition(anyString());

        assertIterableEquals(expected, currentCondition);
        verify(accuWeatherClient).getCurrentCondition(anyString());
    }
}