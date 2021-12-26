package ru.itis.kpfu.telegram.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import ru.itis.kpfu.telegram.config.AWSProperties;
import ru.itis.kpfu.telegram.config.AWSProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FaceScanService {

    private final AmazonS3 client;
    private final AWSProvider clientProvider;
    private final AWSProperties properties;

    public FaceScanService() {
        this.clientProvider = new AWSProvider();
        this.client = clientProvider.createClient();
        this.properties = clientProvider.getProperties();
    }

    public List<S3Object> findByName(String name) {
        var request = new ListObjectsV2Request();
        List<S3Object> result = new ArrayList<>();
        request.withBucketName(properties.getBucket())
                .withPrefix("main/");

        var filtered = client.listObjectsV2(request)
                .getObjectSummaries();
        System.out.println(filtered);
        filtered.forEach(blob -> {
            var meta = client.getObjectMetadata(properties.getBucket(), blob.getKey()).getUserMetadata();
            System.out.println(meta.toString());

            if (meta.get("names") != null && meta.get("names").contains(name)) {
                result.add(client.getObject(properties.getBucket(), blob.getKey()));
            }
        });

        return result;
    }

    public void setName(String key, String name) {
        System.out.println("Key: " + key);
        System.out.println("Name: " + name);
        var metadata = client.getObject(properties.getBucket(), "main/" + key.substring(8, key.lastIndexOf("/")))
                .getObjectMetadata();
        metadata.addUserMetadata("names", metadata.getUserMetaDataOf("names") + name + ",");
        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(properties.getBucket(), key, properties.getBucket(), key)
                .withNewObjectMetadata(metadata);

        client.copyObject(copyObjectRequest);
    }
}
