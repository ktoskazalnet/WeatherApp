package org.ktoskazalnet.repository;

import org.ktoskazalnet.model.accuweather_api.locations.City;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CacheCityRepository {
    private final Map<String, City> cityMapCache = new HashMap<>();

    public void add(City city) {
        cityMapCache.putIfAbsent(city.getKey(), city);
    }

    public Map<String, City> getCache() {
        return Collections.unmodifiableMap(cityMapCache);
    }

    public Optional<City> getByKey(String key) {
        return Optional.ofNullable(cityMapCache.get(key));
    }
}
