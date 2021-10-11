package ru.itis.kpfu.photoalbum.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Zagir Dingizbaev
 */

@Slf4j
@Component
public class ExitCommand extends Command{

    @Override
    public void execute() {
        log.info("Finishing program");
        System.exit(0);
    }

    @Override
    public Headers header() {
        return Headers.EXIT;
    }

    @Override
    public String description() {
        return "\n\tFinish program executing";
    }
}
