package com.osakakuma.opms.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "opms.s3")
public class AwsS3Config {
    /**
     * The region where the s3-bucket resides in
     */
    private String region;
    /**
     * The bucket name to store the OPMS uploaded images
     */
    private String bucket;

    @Bean
    public S3Client s3Client() {
        var credentialsProvider = InstanceProfileCredentialsProvider.builder().build();
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider)
                .build();
    }

    @Bean
    public S3AsyncClient s3AsyncClient() {
        var credentialsProvider = InstanceProfileCredentialsProvider.builder().build();
        return S3AsyncClient.builder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider)
                .build();
    }
}
