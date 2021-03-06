package ru.itis.kpfu.qtrigger;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendPhoto;

public class TelegramSender {

    private final TelegramBot telegramBot;

    public TelegramSender(String botToken) {
        this.telegramBot = new TelegramBot(botToken);
    }

    public void sendPhoto(String chatId, byte[] photoBytes, String captionKey) {
        System.out.println(chatId);
        SendPhoto sendPhoto = new SendPhoto(chatId, photoBytes).caption("кто это? \n" + captionKey);
        telegramBot.execute(sendPhoto);
    }

}
