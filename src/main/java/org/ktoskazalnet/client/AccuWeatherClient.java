package org.ktoskazalnet.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.ktoskazalnet.model.accuweather_api.current_conditions.CurrentConditionRoot;
import org.ktoskazalnet.model.accuweather_api.locations.City;
import org.ktoskazalnet.model.accuweather_api.locations.TopCityCount;

import java.util.List;

public class AccuWeatherClient {
    private final ObjectMapper objectMapper;
    private final OkHttpClient okHttpClient;
    private final String apiKey;

    public AccuWeatherClient(ObjectMapper objectMapper,
                             OkHttpClient okHttpClient,
                             String apiKey) {
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

        TypeReference<List<City>> typeReference = new TypeReference<>() {
        };

        return sendRq(httpUrl, typeReference);
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

        TypeReference<List<CurrentConditionRoot>> typeReference = new TypeReference<>() {
        };

        return sendRq(httpUrl, typeReference);
    }

    private <T> T sendRq(HttpUrl httpUrl, TypeReference<T> tTypeReference) {
        try {
            Request request = new Request.Builder()
                    .url(httpUrl)
                    .build();

            System.out.println("Sending rq: " + request);
            Response response = okHttpClient.newCall(request).execute();
            System.out.println("Receiving rs: " + response);
            String json = response.body().string();

            return objectMapper.readValue(json, tTypeReference);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
