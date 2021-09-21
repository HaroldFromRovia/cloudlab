package ru.itis.kpfu.telegram.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Zagir Dingizbaev
 */

@Service
@RequiredArgsConstructor
public class WeatherService {

    public String get() {
        return "Hello kityy";
    }

}
