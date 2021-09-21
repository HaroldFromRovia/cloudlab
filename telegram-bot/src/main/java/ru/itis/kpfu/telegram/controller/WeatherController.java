package ru.itis.kpfu.telegram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.itis.kpfu.telegram.service.WeatherService;

/**
 * @author Zagir Dingizbaev
 */

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/get")
    public String get(@RequestBody Update update) {
        return "Hello world";
    }

}
