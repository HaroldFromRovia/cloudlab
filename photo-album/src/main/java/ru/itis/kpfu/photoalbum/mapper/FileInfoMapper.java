package ru.itis.kpfu.photoalbum.mapper;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.mapstruct.*;
import ru.itis.kpfu.photoalbum.model.FileInfoDTO;

/**
 * @author Zagir Dingizbaev
 */

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FileInfoMapper {

    @Mapping(source = "size", target = "size", qualifiedByName = "conv-size")
    FileInfoDTO toFileInfoDTO(S3ObjectSummary summary);

    @Named("conv-size")
    default Long convertSize(Long size) {
        return size / (1024);
    }
}
