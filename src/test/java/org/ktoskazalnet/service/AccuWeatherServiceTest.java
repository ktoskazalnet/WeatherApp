package org.ktoskazalnet.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.ktoskazalnet.model.accuweather_api.current_conditions.CurrentConditionRoot;
import org.ktoskazalnet.model.accuweather_api.locations.City;
import org.ktoskazalnet.model.accuweather_api.locations.TopCityCount;
import org.ktoskazalnet.client.AccuWeatherClient;
import org.ktoskazalnet.repository.CacheCityRepository;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccuWeatherServiceTest {
    private final AccuWeatherClient accuWeatherClient = mock(AccuWeatherClient.class);
    private final CacheCityRepository cacheCityRepository = mock(CacheCityRepository.class);
    private final Scanner scanner = new Scanner(new ByteArrayInputStream("1".getBytes()));

    private final AccuWeatherService accuWeatherService =
            new AccuWeatherService(accuWeatherClient, cacheCityRepository, scanner);

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(accuWeatherClient, cacheCityRepository);
    }

    @Test
    void checkWeather_ShouldReturnCurrentConditionPopulateCache() {
        Map<String, City> cityMap = new HashMap<>();
        List<City> cityList = List.of(mock(City.class), mock(City.class));
        List<CurrentConditionRoot> currentConditionRoots = List.of();

        City city = mock(City.class);
        when(city.getKey()).thenReturn("1");

        when(cacheCityRepository.getCache()).thenReturn(cityMap);
        when(accuWeatherClient.getTopCities(TopCityCount.FIFTY)).thenReturn(cityList);
        when(cacheCityRepository.getByKey("1")).thenReturn(Optional.of(city));
        when(accuWeatherClient.getCurrentCondition("1")).thenReturn(currentConditionRoots);

        List<CurrentConditionRoot> actual = accuWeatherService.getCurrentCondition(TopCityCount.FIFTY);

        verify(cacheCityRepository).getCache();
        verify(accuWeatherClient).getTopCities(TopCityCount.FIFTY);
        verify(cacheCityRepository, times(2)).add(any(City.class));
        verify(cacheCityRepository).getByKey("1");
        verify(accuWeatherClient).getCurrentCondition("1");

        assertEquals(actual, currentConditionRoots);
    }
    @Test
    void checkWeather_ShouldReturnCurrentConditionInCache() {
        Map<String, City> cityMap = getCache(51);

        List<CurrentConditionRoot> currentConditionRoots = List.of();

        City city = mock(City.class);
        when(city.getKey()).thenReturn("1");

        when(cacheCityRepository.getCache()).thenReturn(cityMap);
        when(cacheCityRepository.getByKey("1")).thenReturn(Optional.of(city));
        when(accuWeatherClient.getCurrentCondition("1")).thenReturn(currentConditionRoots);

        List<CurrentConditionRoot> actual =
                accuWeatherService.getCurrentCondition(TopCityCount.FIFTY);

        verify(cacheCityRepository).getCache();
        verify(cacheCityRepository).getByKey("1");
        verify(accuWeatherClient).getCurrentCondition("1");

        assertEquals(actual, currentConditionRoots);
    }

    private Map<String, City> getCache(int count) {
        Map<String, City> cityMap = new HashMap<>();

        for (int i = 0; i < count; i++) {
            String key = String.valueOf(i);
            cityMap.put(key, new City(key, key));
        }

        return cityMap;
    }

}