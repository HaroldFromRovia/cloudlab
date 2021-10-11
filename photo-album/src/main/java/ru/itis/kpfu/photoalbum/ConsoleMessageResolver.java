package ru.itis.kpfu.photoalbum;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.MissingCommandException;
import com.beust.jcommander.ParameterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.itis.kpfu.photoalbum.command.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Zagir Dingizbaev
 */

@Slf4j
@Component
public class ConsoleMessageResolver {

    private final ApplicationContext context;
    private final Map<String, Command> commandMap = new HashMap<>();

    public ConsoleMessageResolver(ApplicationContext context) {
        this.context = context;
        initializeCommands();
    }

    private void initializeCommands() {
        var commands = context.getBeansOfType(Command.class);
        commands.forEach((header, command) -> addCommand(command));
    }

    private void addCommand(Command command) {
        commandMap.put(command.header().name().toLowerCase(), command);
    }

    public Map<String, Command> getCommandMap() {
        return commandMap;
    }

    public void executeCommand(String[] args) {
        String parsed = "help";
        var jCommander = initJcommander();

        try {
            jCommander.parse(args);
            parsed = jCommander.getParsedCommand();
        } catch (MissingCommandException e) {
            log.error("Illegal command \"{}\". Use help command to see list of allowed inputs," +
                            " or add quotes if your params contains whitespaces",
                    e.getUnknownCommand());
        } catch (ParameterException e) {
            log.error("Could not execute command due to illegal parameters.\n" +
                    "{}", e.getMessage());
        }

        Command command = commandMap.get(parsed);
        command.execute();
    }

    private JCommander initJcommander(){
        var builder = JCommander.newBuilder();
        context.getBean(ConsoleMessageResolver.class).getCommandMap()
                .forEach((header, command) -> builder.addCommand(command.header()
                        .name()
                        .toLowerCase(), command));
        return builder.build();
    }
}
