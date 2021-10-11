package ru.itis.kpfu.photoalbum.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Zagir Dingizbaev
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDTO {

    private String name;
    private List<FileInfoDTO> files;
}
