package ru.itis.kpfu.telegram.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Zagir Dingizbaev
 */

@Configuration
public class RootConfig {

    @Bean
    public ObjectMapper objectMapper(){
        var objectMapper = new ObjectMapper();
        return objectMapper;
    }

}
