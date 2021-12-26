package ru.itis.kpfu.telegram.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import ru.itis.kpfu.telegram.model.Message;
import ru.itis.kpfu.telegram.model.Response;
import ru.itis.kpfu.telegram.model.TelegramBotBuilder;
import ru.itis.kpfu.telegram.model.Update;

import java.util.Objects;

public class TelegramService {

    private final TelegramBot bot = TelegramBotBuilder.builder().build();
    private final FaceScanService faceScanService = new FaceScanService();

    public Response process(Update update) {
        try {
            var message = update.getMessage();
            parseMessage(message);
        } catch (Exception e) {
            System.out.println(update.toString());
            return Response.builder()
                    .statusCode(500)
                    .body("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    private Response parseMessage(Message message){
        var reply = message.getReplyToMessage();
        var chat = message.getChat();
        var messageText = message.getText();

        if(message.getText().startsWith("/find")){
            faceScanService.findByName(messageText.substring(messageText.indexOf(" ")));

            return Response.builder()
                    .statusCode(200)
                    .build();
        }

        if(Objects.nonNull(reply)){
            faceScanService.setName(reply.getText());
        }
    }

}
