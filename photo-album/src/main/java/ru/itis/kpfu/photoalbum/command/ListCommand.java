package ru.itis.kpfu.photoalbum.command;

import com.beust.jcommander.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.itis.kpfu.photoalbum.model.FileInfoDTO;
import ru.itis.kpfu.photoalbum.service.FileService;

import java.util.List;

/**
 * @author Zagir Dingizbaev
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class ListCommand extends Command {

    private final FileService fileService;

    @Parameter(names = {"-a", "-album"})
    private String album;

    @Override
    public void execute() {
        List<FileInfoDTO> files;

        if (album == null) {
            files = fileService.listAlbums();
            files.forEach(a -> log.info("{}", a.getKey().replace("/", "")));
        } else {
            files = fileService.listPhotos(album);
            files.forEach(a -> log.info("{} {} KB", a.getKey().substring(a.getKey().lastIndexOf("/") + 1), a.getSize()));
        }

        album = null;
    }

    @Override
    public Headers header() {
        return Headers.LIST;
    }

    @Override
    public String description() {
        return "\n\tList all albums, or photos in album\n" +
                "\t\tUsage:\n" +
                "\t\t\t(1) -a album_name | -album album_name";
    }
}
