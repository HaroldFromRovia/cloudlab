package ru.itis.kpfu.facescan.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.json.JSONObject;
import ru.itis.kpfu.facescan.Handler;
import ru.itis.kpfu.facescan.models.dto.*;
import ru.itis.kpfu.facescan.models.properties.AWSProperties;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Zagir Dingizbaev
 */

public class FaceScanService {

    private final AmazonS3 client;
    private final AmazonSQS sqsClient;
    private final AWSProperties properties;
    private final String faceSearchUrl = "https://vision.api.cloud.yandex.net/vision/v1/batchAnalyze";
    private final String resultPath = "analyze/";
    private final ClientProvider clientProvider;

    public FaceScanService() {
        this.clientProvider = new ClientProvider();
        this.sqsClient = clientProvider.createSqsClient();
        this.client = clientProvider.createClient();
        this.properties = clientProvider.getProperties();
    }

    @SneakyThrows
    public String scan(EventDTO eventDto) {
        var message = eventDto.getMessages().get(0);
        var key = message.getDetails().getObjectId();

        GetObjectRequest request = new GetObjectRequest(properties.getBucket(), key);
        S3Object object = client.getObject(request);

        if (isCreateEvent(message)) {
            String response = uploadToScan(object);

            List<Vertices> coordinates = getCoordinates(response);
            BufferedImage image;
            try (InputStream is = client.getObject(request).getObjectContent()) {
                image = ImageIO.read(is);
                processImage(coordinates, image, key);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "Success";
    }

    @SneakyThrows
    private String uploadToScan(S3Object object) {
        var bytes = Base64.encodeBase64String(object.getObjectContent().readAllBytes());

        ScanRequest scanRequest = ScanRequest.builder()
                .folder(properties.getFolder())
                .analyzeSpecsWrapper(new AnalyzeSpecsWrapper(Collections.singletonList(AnalyzeSpecs.builder()
                        .content(bytes)
                        .features(Collections.singletonList(Feature.builder()
                                .type("FACE_DETECTION")
                                .build()))
                        .build())))
                .build();

        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "Api-Key " + properties.getApiSecret())
                .header("Content-Type", "application/json")
                .uri(URI.create(faceSearchUrl))
                .POST(HttpRequest.BodyPublishers.ofString(Handler.objectMapper.writeValueAsString(scanRequest)))
                .build();
        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    @SneakyThrows
    private List<Vertices> getCoordinates(String response) {
        JSONObject object = new JSONObject(response);
        List<Vertices> vertices = new ArrayList<>();
        var faces = object.getJSONArray("results")
                .getJSONObject(0)
                .getJSONArray("results")
                .getJSONObject(0)
                .getJSONObject("faceDetection")
                .getJSONArray("faces");

        for (int i = 0; i < faces.length(); i++) {
            vertices.add(Handler.objectMapper.readValue(faces.getJSONObject(i).getJSONObject("boundingBox").toString(), Vertices.class));
        }

        return vertices;
    }

    @SneakyThrows
    private void processImage(List<Vertices> vertices, BufferedImage image, String fileName) {
        for (int i = 0; i < vertices.size(); i++) {
            var coordinates = vertices.get(i).getCoordinates();
            var width = Math.abs(coordinates.get(3).getX() - coordinates.get(0).getX());
            var height = Math.abs(coordinates.get(0).getY() - coordinates.get(1).getY());
            var key = resultPath + fileName.replace("main/", "") + "/" + i + ".jpeg";
            BufferedImage subImage = image.getSubimage(coordinates.get(0).getX(), coordinates.get(0).getY(), width, height);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(subImage, "jpeg", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(os.size());

            PutObjectRequest putObjectRequest = new PutObjectRequest(properties.getBucket(), key, is, metadata);
            SendMessageRequest sqsRequest = new SendMessageRequest();
            sqsRequest.withMessageBody(key);
            sqsRequest.withQueueUrl(properties.getQueueUrl());

            client.putObject(putObjectRequest);
            sqsClient.sendMessage(sqsRequest);

        }
    }

    private boolean isCreateEvent(Message message) {
        return message.getEventMetadata().getEventType().equals(EventTypes.CREATE.getEventName());
    }
}
