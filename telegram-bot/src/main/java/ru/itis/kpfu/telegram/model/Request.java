package ru.itis.kpfu.telegram.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Request {

    private String httpMethod;
    private String body;
}
