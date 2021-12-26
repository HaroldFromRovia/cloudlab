package ru.itis.kpfu.photoalbum.command;

import com.beust.jcommander.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.itis.kpfu.photoalbum.service.FileService;

import java.io.IOException;

/**
 * @author Zagir Dingizbaev
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class UploadCommand extends Command {

    @Parameter(names = {"-p", "-path"})
    private String path;
    @Parameter(names = {"-a", "-album"}, required = true)
    private String album;

    private final FileService fileService;

    @Override
    public void execute() {
        fileService.upload(path, album);
    }

    @Override
    public Headers header() {
        return Headers.UPLOAD;
    }

    @Override
    public String description() {
        return "\n\tCommand to upload files to specified album.\n" +
                "\t\tUsage:\n" +
                "\t\t\t(1) -p path | -path path\n" +
                "\t\t\t(2) -a album_name | -album album_name";
    }
}
