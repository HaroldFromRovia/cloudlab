package ru.itis.kpfu.photoalbum.command;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.itis.kpfu.photoalbum.ConsoleMessageResolver;

import java.util.Locale;

/**
 * @author Zagir Dingizbaev
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class HelpCommand extends Command {

    private final ApplicationContext context;

    @Override
    public void execute() {
        var headers = Headers.values();
        for (Headers header : headers) {
            String headerName = header.name().toLowerCase();
            Command command = context.getBean(ConsoleMessageResolver.class).getCommandMap().get(headerName);
            System.out.println((headerName + command.description()));
        }
    }

    @Override
    public Headers header() {
        return Headers.HELP;
    }

    @Override
    public String description() {
        return "\n\tShow all commands and their descriptions\n" +
                "\t\tUsage:\n" +
                "\t\t\t(1) help";
    }
}
