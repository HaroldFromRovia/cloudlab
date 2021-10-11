package ru.itis.kpfu.photoalbum;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * @author Zagir Dingizbaev
 */

@Component
@RequiredArgsConstructor
public class MessageHandler {

    private final ConsoleMessageResolver resolver;

    public void start() {
        var scanner = new Scanner(System.in);
        while (true) {
            resolver.executeCommand(scanner.nextLine().split(" "));
        }

    }

}
