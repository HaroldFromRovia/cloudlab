package ru.itis.kpfu.telegram.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.HttpHead;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Audio;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.itis.kpfu.telegram.model.dto.Response;
import ru.itis.kpfu.telegram.model.dto.SpeechKitDto;

/**
 * @author Zagir Dingizbaev
 */

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${yandex.speech-kit.url}")
    private String yandexSpeechKitURL;
    @Value("yandex.speech-kit.key")
    private String yandexSTTKey;

    public Response getWeatherByAudio(Update update) {
        var message = update.getMessage();
        var chatId = message.getChatId();
        var audioFile = message.getAudio();
        var text = speechToText(audioFile);

        return Response.builder()
                .chatId(chatId)
                .text(text.getResult())
                .build();
    }

    private SpeechKitDto speechToText(Audio audio) {
        var headers = new HttpHeaders();
        headers.add();
        return restTemplate.postForObject(yandexSpeechKitURL, audio, SpeechKitDto.class);
    }

}
