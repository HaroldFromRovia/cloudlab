package ru.itis.kpfu.telegram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.itis.kpfu.telegram.model.dto.Response;
import ru.itis.kpfu.telegram.service.WeatherService;

import javax.annotation.PostConstruct;

/**
 * @author Zagir Dingizbaev
 */

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @PostMapping("/getWeather")
    public Response getWeather(@RequestBody Update update) {
        return weatherService.getWeatherByAudio(update);
    }

}
