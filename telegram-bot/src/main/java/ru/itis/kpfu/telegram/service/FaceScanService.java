package ru.itis.kpfu.telegram.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectMetadataRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.S3Object;
import ru.itis.kpfu.telegram.config.AWSProperties;
import ru.itis.kpfu.telegram.config.AWSProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FaceScanService {

    private final AmazonS3 client;
    private final AWSProvider clientProvider;
    private final AWSProperties properties;
    private final TelegramService telegramService;

    public FaceScanService() {
        this.clientProvider = new AWSProvider();
        this.telegramService = new TelegramService();
        this.client = clientProvider.createClient();
        this.properties = clientProvider.getProperties();
    }

    public List<S3Object> findByName(String name) {
        var request = new ListObjectsV2Request();
        List<S3Object> result = new ArrayList<>();
        request.withBucketName(properties.getBucket());

        var filtered = client.listObjectsV2(request)
                .getObjectSummaries()
                .stream()
                .filter(blob -> !blob.getKey().contains("/"))
                .collect(Collectors.toList());
        filtered.forEach(blob -> {
            var meta = client.getObjectMetadata(properties.getBucket(), blob.getKey());
            if (meta.getUserMetadata().get("names").contains(name)) {
                result.add(client.getObject(properties.getBucket(), blob.getKey()));
            }
        });

        return result;
    }

    public void setName(String name, String text) {

    }
}
