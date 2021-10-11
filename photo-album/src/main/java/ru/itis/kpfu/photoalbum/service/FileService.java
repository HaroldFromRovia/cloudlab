package ru.itis.kpfu.photoalbum.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.itis.kpfu.photoalbum.mapper.FileInfoMapper;
import ru.itis.kpfu.photoalbum.model.FileInfoDTO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Zagir Dingizbaev
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final AmazonS3 client;
    private final FileInfoMapper mapper;

    @Value("${cloud.aws.bucket}")
    private String bucket;
    private final String[] extensions = {"jpeg", "jpg"};

    public List<FileInfoDTO> listPhotos(String album) {
        if (!client.doesObjectExist(bucket, album + "/")) {
            log.warn("Specified album doesn't exists");
            return Collections.emptyList();
        }
        var request = new ListObjectsV2Request();
        request.withBucketName(bucket)
                .withPrefix(album + "/");

        return getListResult(request)
                .stream()
                .filter(f -> (f.getKey().toLowerCase().endsWith(".jpeg") || f.getKey().toLowerCase().endsWith(".jpg")))
                .collect(Collectors.toList());
    }

    public List<FileInfoDTO> listAlbums() {
        var request = new ListObjectsV2Request();
        request.withBucketName(bucket);

        return getListResult(request)
                .stream()
                .filter(f -> !(f.getKey().toLowerCase().endsWith(".jpeg") || f.getKey().toLowerCase().endsWith(".jpg")))
                .collect(Collectors.toList());
    }

    public void download(String path, String album) throws IOException {
        if (!doesExist(path)) {
            log.error("Specified path is not valid");
            return;
        }
        List<FileInfoDTO> photos = listPhotos(album);
        List<GetObjectRequest> requests = new ArrayList<>();
        for (FileInfoDTO fileInfo : photos) {
            requests.add(new GetObjectRequest(bucket, fileInfo.getKey()));
        }

        for (GetObjectRequest request : requests) {
            S3Object downloaded = client.getObject(request);
            String fileName = downloaded.getKey().substring(downloaded.getKey().lastIndexOf("/"));
            log.info("Downloading file to {}", fileName);
            FileUtils.copyInputStreamToFile(downloaded.getObjectContent(), new File(path + fileName));
        }
        if (!photos.isEmpty())
            log.info("Downloading process has finished");
    }

    public void upload(String path, String album) {
        List<PutObjectRequest> requests = new ArrayList<>();
        for (File file : getFilesFromFolder(path)) {
            requests.add(new PutObjectRequest(bucket, album + "/" + file.getName(), file));
        }

        for (PutObjectRequest request : requests) {
            log.info("Uploading file {}", request.getKey());
            client.putObject(request);
        }
    }

    private List<FileInfoDTO> getListResult(ListObjectsV2Request request) {
        return client.listObjectsV2(request)
                .getObjectSummaries()
                .stream()
                .map(mapper::toFileInfoDTO)
                .collect(Collectors.toList());
    }

    private boolean doesExist(String path) {
        File file = new File(path);
        return file.isDirectory();
    }

    private List<File> getFilesFromFolder(String path) {
        if (!doesExist(path)) {
            log.error("Specified path is not valid");
            return new ArrayList<>();
        }
        return (List<File>) FileUtils.listFiles(new File(path ), extensions, false);
    }

}
