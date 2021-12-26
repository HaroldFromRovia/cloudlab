package ru.itis.kpfu.telegram.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.SneakyThrows;
import ru.itis.kpfu.telegram.model.Message;
import ru.itis.kpfu.telegram.model.Response;
import ru.itis.kpfu.telegram.model.TelegramBotBuilder;
import ru.itis.kpfu.telegram.model.Update;

import java.io.InputStream;
import java.util.Objects;

public class TelegramService {

    private final TelegramBot bot = TelegramBotBuilder.builder().build();
    private final FaceScanService faceScanService = new FaceScanService();

    public Response process(Update update) {
        try {
            var message = update.getMessage();
            return parseMessage(message);
        } catch (Exception e) {
            System.out.println(update.toString());
            return Response.builder()
                    .statusCode(500)
                    .body("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    private Response parseMessage(Message message) {
        var reply = message.getReplyToMessage();
        var chat = message.getChat();
        var messageText = message.getText();

        if (message.getText().startsWith("/find")) {
            faceScanService.findByName(messageText.substring(messageText.indexOf(" ") + 1))
                    .forEach(blob -> sendToBot(chat.getId(), blob.getObjectContent()));

            return Response.builder()
                    .statusCode(200)
                    .build();
        }

        if (Objects.nonNull(reply)) {
            faceScanService.setName(messageText, reply.getText());
            return Response.builder()
                    .statusCode(200)
                    .build();
        }

        bot.execute(new SendMessage(message.getChat().getId(), "Cannot' recognize command"));

        return Response.builder()
                .statusCode(500)
                .build();
    }

    @SneakyThrows
    public void sendToBot(Long chatId, InputStream is) {
        SendDocument sendRequest = new SendDocument(chatId, is.readAllBytes());
        bot.execute(sendRequest);
    }

}
