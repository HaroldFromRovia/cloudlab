package ru.itis.kpfu.telegram.service;

import com.pengrad.telegrambot.model.Update;
import lombok.SneakyThrows;
import org.apache.http.client.utils.URIBuilder;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Service {

    //TODO replace hardcoded
    private final String defaultBotURL = "https://api.telegram.org/bot1972227699:AAHYtGkMf7YLyrpjCQEVuKndfj0HRUXJ2hY/sendMessage";

    @SneakyThrows
    public void get(Update update) {
        var client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        var uri = new URIBuilder(defaultBotURL)
                .addParameter("chat_id", String.valueOf(update.message().chat().id()))
                .addParameter("text", "Hello world")
                .build();
        var request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

}
