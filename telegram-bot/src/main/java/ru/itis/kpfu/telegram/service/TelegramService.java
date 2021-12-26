package ru.itis.kpfu.telegram.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.itis.kpfu.telegram.model.TelegramBotBuilder;

public class TelegramService {

    private final TelegramBot bot = TelegramBotBuilder.builder().build();

    public void resolve(Update update) {
        try {
            var sendMessage = new SendMessage(update.message().chat().id(), update.message().text());
            bot.execute(sendMessage);
        } catch (Exception e) {
            System.out.println(update.toString());
        }

    }

}
