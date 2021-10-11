package ru.itis.kpfu.photoalbum.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Zagir Dingizbaev
 */

@Data
@ConfigurationProperties(prefix = "cloud.aws")
public class AWSProperties {

    private String accessKey;
    private String secretKey;
    private String endpoint;
    private String bucket;
    private String region;
}
