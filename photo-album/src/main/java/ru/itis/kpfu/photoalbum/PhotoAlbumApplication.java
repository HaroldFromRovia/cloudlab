package ru.itis.kpfu.photoalbum;

import com.beust.jcommander.JCommander;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableConfigurationProperties
public class PhotoAlbumApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        new SpringApplicationBuilder(PhotoAlbumApplication.class)
                .logStartupInfo(false)
                .run(args);
    }

    @Override
    public void run(String... args) {
        var handler = context.getBean(MessageHandler.class);
        handler.start();
    }
}
