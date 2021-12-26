package ru.itis.kpfu.qtrigger;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import yandex.cloud.sdk.functions.Context;
import yandex.cloud.sdk.functions.YcFunction;

import java.io.IOException;
import java.util.Optional;

public class Handler implements YcFunction<String, String> {
    @Override
    public String handle(String eventJson, Context context) {
        String id = System.getenv("AWS_ACCESS_KEY_ID");
        String secret = System.getenv("AWS_SECRET_ACCESS_KEY");
        String chatId = Optional.ofNullable(System.getenv("default_chat_id")).orElse("522992070");
        String botToken = System.getenv("BOT_TOKEN");
        String bucketName = Optional.ofNullable(System.getenv("BUCKET")).orElse("facescan-storage");
        AmazonS3 amazonS3 = null;
        try {
            amazonS3 = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(
                            new BasicAWSCredentials(id, secret)))
                    .withEndpointConfiguration(
                            new AmazonS3ClientBuilder.EndpointConfiguration(
                                    "storage.yandexcloud.net", "ru-central1"
                            )
                    )
                    .build();
        } catch (Exception e) {
            System.err.println("can't create s3 instance");
            e.printStackTrace();
        }

        EventParser eventParser = new EventParser();
        String objectKey = eventParser.getObjectKey(eventJson);
        byte[] photoBytes = null;
        try {
             photoBytes = amazonS3.getObject(bucketName, objectKey).getObjectContent().readAllBytes();
        } catch (IOException e) {
            System.err.println("can't get object bytes by key from the message (update)");
        }
        try {
            chatId = amazonS3.listObjectsV2(bucketName, "users").getObjectSummaries().get(0).getKey();
        }
        catch (Exception e) {
            System.err.println("can't get chatId from object storage");
        }

        TelegramSender telegramSender = new TelegramSender(botToken);
        telegramSender.sendPhoto(chatId, photoBytes, objectKey);
        return "ok";
    }
}