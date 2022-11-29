package org.ktoskazalnet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import org.ktoskazalnet.client.AccuWeatherClient;
import org.ktoskazalnet.model.accuweather_api.current_conditions.CurrentConditionRoot;
import org.ktoskazalnet.model.accuweather_api.locations.TopCityCount;
import org.ktoskazalnet.repository.CacheCityRepository;
import org.ktoskazalnet.service.AccuWeatherService;

import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        OkHttpClient okHttpClient = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        Scanner scanner = new Scanner(System.in);

        AccuWeatherClient accuWeatherClient =
                new AccuWeatherClient(objectMapper, okHttpClient, args[0]);
        CacheCityRepository cityRepository = new CacheCityRepository();

        AccuWeatherService accuWeatherService =
                new AccuWeatherService(accuWeatherClient, cityRepository, scanner);

        start(accuWeatherService, scanner);
    }

    public static void start(AccuWeatherService accuWeatherService, Scanner scanner) {
        try (scanner) {
            while (true) {
                System.out.println("How many cities do you want to see? (50/100/150)");
                int count = Integer.parseInt(scanner.nextLine());

                List<CurrentConditionRoot> currentConditionRoots =
                        accuWeatherService.getCurrentCondition(TopCityCount.valueOf(count));

                System.out.println(currentConditionRoots);

                System.out.println("Do you want to check weather in another city? Y - yes, N - no");
                String answer = scanner.nextLine();

                if (answer.equalsIgnoreCase("N")) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
