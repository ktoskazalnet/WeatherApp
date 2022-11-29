package org.ktoskazalnet.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.ktoskazalnet.model.accuweather_api.current_conditions.CurrentConditionRoot;
import org.ktoskazalnet.exception.CityNotFoundException;
import org.ktoskazalnet.model.accuweather_api.locations.City;
import org.ktoskazalnet.model.accuweather_api.locations.TopCityCount;

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
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("dataservice.accuweather.com")
                .addPathSegment("locations")
                .addPathSegment("v1")
                .addPathSegment("topcities")
                .addPathSegment(String.valueOf(topCityCount.getCount()))
                .addQueryParameter("apikey", apiKey)
                .build();

        Request request = getRequest(httpUrl);

        try {
            System.out.println("Sending rq: " + request);
            Response response = okHttpClient.newCall(request).execute();
            System.out.println("Receiving rs: " + response);
            String json = response.body().string();
            return objectMapper.readValue(json, new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Request getRequest(HttpUrl httpUrl) {
        return new Request.Builder()
                .url(httpUrl)
                .build();
    }

    public List<CurrentConditionRoot> getCurrentCondition(String key) {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("dataservice.accuweather.com")
                .addPathSegment("currentconditions")
                .addPathSegment("v1")
                .addPathSegment(key)
                .addQueryParameter("apikey", apiKey)
                .build();

        Request request = getRequest(httpUrl);

        try {
            System.out.println("Sending rq: " + request);
            Response response = okHttpClient.newCall(request).execute();
            System.out.println("Receiving rs: " + response);
            String json = response.body().string();
           return objectMapper.readValue(json, new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
