package ru.itis.kpfu.facescan;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import ru.itis.kpfu.facescan.models.dto.EventDTO;
import ru.itis.kpfu.facescan.services.FaceScanService;

import java.util.function.Function;


public class Handler implements Function<String, String> {

    public final static ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public String apply(String eventDTO) {
        FaceScanService service = new FaceScanService();
        return service.scan(objectMapper.readValue(eventDTO, EventDTO.class));
    }
}
