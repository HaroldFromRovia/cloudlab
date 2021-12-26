package ru.itis.kpfu.telegram.service;

import com.pengrad.telegrambot.TelegramBot;
import ru.itis.kpfu.telegram.model.Response;
import ru.itis.kpfu.telegram.model.TelegramBotBuilder;
import ru.itis.kpfu.telegram.model.Update;

public class TelegramService {

    private final TelegramBot bot = TelegramBotBuilder.builder().build();

    public Response resolve(Update update) {
        try {
            var message = update.getMessage().getText();
            var reply = update.getMessage().getReplyToMessage();

//            bot.execute(sendMessage);
            return Response.builder()
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            System.out.println(update.toString());
            return Response.builder()
                    .build();
        }
    }

}
