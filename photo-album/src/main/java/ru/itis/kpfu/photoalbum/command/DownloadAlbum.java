package ru.itis.kpfu.photoalbum.command;

import com.beust.jcommander.Parameter;
import lombok.Getter;
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
public class DownloadAlbum extends Command {

    @Parameter(names = {"-p", "-path"}, required = true)
    private String path;
    @Parameter(names = {"-a", "-album"}, required = true)
    private String album;

    private final FileService fileService;

    @Override
    public void execute() {
        try {
            fileService.download(path, album);
        } catch (IOException e) {
            log.error("Error while downloading file.\n" +
                    "{}", e.getMessage());
        }
    }

    @Override
    public Headers header() {
        return Headers.DOWNLOAD;
    }

    @Override
    public String description() {
        return "\n\tCommand to download the whole album.\n" +
                "\t\tUsage:\n" +
                "\t\t\t(1) -p path | -path path\n" +
                "\t\t\t(2) -a album_name | -album album_name";
    }
}
