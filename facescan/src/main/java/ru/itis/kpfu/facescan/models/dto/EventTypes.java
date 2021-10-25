package ru.itis.kpfu.facescan.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Zagir Dingizbaev
 */

@AllArgsConstructor
public enum EventTypes {
    DELETE("yandex.cloud.events.storage.ObjectDelete"),
    CREATE("yandex.cloud.events.storage.ObjectCreate");

    @Getter
    private String eventName;
}
