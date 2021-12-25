package ru.itis.kpfu.telegram;

import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.itis.kpfu.telegram.model.Response;
import ru.itis.kpfu.telegram.service.Service;

import java.util.function.Function;

public class Handler implements Function<Update, Response> {

    private final Service service = new Service();

    @SneakyThrows
    @Override
    public Response apply(Update update) {
        service.get(update);

        return Response.builder().build();
    }
}
