package org.ktoskazalnet.service;

import org.ktoskazalnet.client.AccuWeatherClient;
import org.ktoskazalnet.exception.CityNotFoundException;
import org.ktoskazalnet.model.accuweather_api.current_conditions.CurrentConditionRoot;
import org.ktoskazalnet.model.accuweather_api.locations.City;
import org.ktoskazalnet.model.accuweather_api.locations.TopCityCount;
import org.ktoskazalnet.repository.CacheCityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AccuWeatherService {
    private final AccuWeatherClient accuWeatherClient;
    private final CacheCityRepository cacheCityRepository;
    private final Scanner scanner;

    public AccuWeatherService(AccuWeatherClient accuWeatherClient,
                              CacheCityRepository cacheCityRepository,
                              Scanner scanner) {
        this.accuWeatherClient = accuWeatherClient;
        this.cacheCityRepository = cacheCityRepository;
        this.scanner = scanner;
    }

    public List<CurrentConditionRoot> getCurrentCondition(TopCityCount topCityCount) {
        Map<String, City> cache = cacheCityRepository.getCache();

        if (topCityCount.getCount() > cache.size()) {
            System.out.println("topCityCount > cache.size, populating cache...");
            accuWeatherClient.getTopCities(topCityCount).forEach(cacheCityRepository::add);
            System.out.println("Cache was populated: " + cache);
        }

        System.out.println(cache);
        System.out.println("Choose the city by key...");

        String cityKey = scanner.nextLine();

        City city = cacheCityRepository.getByKey(cityKey)
                .orElseThrow(() ->
                        new CityNotFoundException("City with id " + cityKey + " not found"));

        return accuWeatherClient.getCurrentCondition(city.getKey());
    }
}
