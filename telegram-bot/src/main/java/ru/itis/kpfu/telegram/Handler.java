package ru.itis.kpfu.telegram;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.model.Update;
import lombok.SneakyThrows;
import ru.itis.kpfu.telegram.model.Response;
import ru.itis.kpfu.telegram.service.Service;

import java.util.function.Function;

public class Handler implements Function<String, Response> {

    private final Service service = new Service();
    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @SneakyThrows
    @Override
    public Response apply(String request) {

        System.out.println(request);
        Update update = mapper.readValue(request, Update.class);
        service.get(update);

        return Response.builder().build();
    }
}
