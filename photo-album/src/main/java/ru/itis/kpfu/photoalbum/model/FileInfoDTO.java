package ru.itis.kpfu.photoalbum.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zagir Dingizbaev
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileInfoDTO {

    private String key;
    private Long size;
}
