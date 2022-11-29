package org.ktoskazalnet.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.ktoskazalnet.accuweather_api.current_conditions.Root;
import org.ktoskazalnet.accuweather_api.exception.CityNotFoundException;
import org.ktoskazalnet.accuweather_api.locations.City;
import org.ktoskazalnet.accuweather_api.locations.TopCityCount;

import java.util.List;

public class AccuWeatherClient {
    private final ObjectMapper objectMapper;
    private final OkHttpClient okHttpClient;
    private final String apiKey;

    public AccuWeatherClient(ObjectMapper objectMapper, OkHttpClient okHttpClient, String apiKey) {
        this.objectMapper = objectMapper;
        this.okHttpClient = okHttpClient;
        this.apiKey = apiKey;
    }

    public List<City> getTopCities(TopCityCount topCityCount) {
        String json;
        List<City> cityList;
        int count = topCityCount.getCount();

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("dataservice.accuweather.com")
                .addPathSegment("locations")
                .addPathSegment("v1")
                .addPathSegment("topcities")
                .addPathSegment("" + count)
                .addQueryParameter("apikey", apiKey)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            json = response.body().string();
            cityList = objectMapper.readValue(json,
                    new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return cityList;
    }

    public List<Root> getCurrentCondition(String cityName) {
        String json;
        List<Root> conditionsList;
        List<City> cities = getTopCities(TopCityCount.ONE_HUNDRED_FIFTY);
        String key = null;

        for (City city : cities) {
            if (city.getEnglishName().equals(cityName)) {
                key = city.getKey();
            }
        }

        if (key == null) {
            try {
                throw new CityNotFoundException();
            } catch (CityNotFoundException e) {
                e.printStackTrace();
            }
        }

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("dataservice.accuweather.com")
                .addPathSegment("currentconditions")
                .addPathSegment("v1")
                .addPathSegment(key)
                .addQueryParameter("apikey", apiKey)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            json = response.body().string();
            conditionsList = objectMapper.readValue(json,
                    new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return conditionsList;
    }
}
