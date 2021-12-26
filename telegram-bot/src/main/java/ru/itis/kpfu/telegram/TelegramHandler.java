package ru.itis.kpfu.telegram;

import lombok.SneakyThrows;
import ru.itis.kpfu.telegram.model.Request;
import ru.itis.kpfu.telegram.model.Response;
import ru.itis.kpfu.telegram.model.Update;
import ru.itis.kpfu.telegram.service.TelegramService;
import ru.itis.kpfu.telegram.utils.JsonMapper;

import java.util.function.Function;

public class TelegramHandler implements Function<Request, Response> {

    private final TelegramService telegramService = new TelegramService();

    @SneakyThrows
    @Override
    public Response apply(Request request) {
        Update update = JsonMapper.fromJson(request.getBody(), Update.class);
        telegramService.process(update);

        return Response.builder()
                .statusCode(200)
                .build();
    }
}
