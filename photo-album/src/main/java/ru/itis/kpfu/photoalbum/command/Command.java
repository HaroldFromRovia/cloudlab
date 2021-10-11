package ru.itis.kpfu.photoalbum.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Zagir Dingizbaev
 */

@Slf4j
@Component
public abstract class Command {

    public abstract void execute();

    public abstract Headers header();

    public enum Headers {
        UPLOAD,
        DOWNLOAD,
        LIST,
        HELP,
        EXIT
    }

    public abstract String description();

}
