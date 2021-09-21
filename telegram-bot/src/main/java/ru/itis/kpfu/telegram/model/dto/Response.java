package ru.itis.kpfu.telegram.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zagir Dingizbaev
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    private final String method = "sendMessage";
    private String text;
    @JsonProperty("chat_id")
    private Long chatId;

}
