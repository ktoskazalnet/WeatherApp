package org.ktoskazalnet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import org.ktoskazalnet.accuweather_api.locations.TopCityCount;
import org.ktoskazalnet.client.AccuWeatherClient;
import org.ktoskazalnet.service.AccuWeatherService;

import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        OkHttpClient okHttpClient = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        AccuWeatherClient accuWeatherClient = new AccuWeatherClient(objectMapper, okHttpClient, args[0]);
        AccuWeatherService accuWeatherService = new AccuWeatherService(accuWeatherClient);

        start(accuWeatherService);
    }

    public static void start(AccuWeatherService accuWeatherService) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("How many cities do you want to see? (50 - 100 - 150)");
            int count = scanner.nextInt();
            System.out.println(accuWeatherService.getCitiesList(TopCityCount.valueOf(count)));
            System.out.println("Choose city: ");
            String cityName = scanner.next();
            System.out.println(accuWeatherService.checkWeather(cityName));
            System.out.println("Do you want to check weather in another city? Y - yes, N - no");
            String answer = scanner.next();

            if (answer.equalsIgnoreCase("N")) {
                break;
            }
        }
    }
}
