package pl.filmveeb.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.filmveeb.dto.UserDto;
import pl.filmveeb.model.weather.FullWeatherInfo;

@Service
public class WeatherService {

    private final String openWeatherApiKey = "c8d21edcb8d681952c9139e3780454a2";
    private final RestTemplate restTemplate;
    private final UserService userService;

    public WeatherService(RestTemplate restTemplate, UserService userService) {
        this.restTemplate = restTemplate;
        this.userService = userService;
    }

    public double getCityTemperature(String city, String country) {
        String basicWeatherUrlTemplate = "https://api.openweathermap.org/data/2.5/weather?q=%s,q=%s&units=metric&appid=%s";
        String fullUrl = String.format(basicWeatherUrlTemplate, city, country, openWeatherApiKey);
        FullWeatherInfo fullWeatherInfo = restTemplate.getForObject(fullUrl, FullWeatherInfo.class);
        return fullWeatherInfo != null ? fullWeatherInfo.getMain().getTemp() : 0;
    }

    public String getCityWeather() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto userByEmial = userService.getUserByEmial(userName);
        double cityTemperature = getCityTemperature(userByEmial.getCity(), "pl");
        return userByEmial.getCity() + " " + String.format("%.1f", cityTemperature) + "\u00B0C";
    }
}
