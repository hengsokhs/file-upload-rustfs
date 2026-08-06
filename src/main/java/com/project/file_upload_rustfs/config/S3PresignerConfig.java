package com.project.file_upload_rustfs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;


public class S3PresignerConfig {
//  @Bean(destroyMethod = "close")
//  S3Presigner s3Presigner(S3Properties properties) {
//    AwsBasicCredentials credentials = AwsBasicCredentials.create(
//        properties.getAccessKey(),
//        properties.getSecretKey()
//    );
//
//    return S3Presigner.builder()
//        .endpointOverride(properties.getEndpoint())
//        .region(Region.of(properties.getRegion()))
//        .credentialsProvider(
//            StaticCredentialsProvider.create(credentials)
//        )
//        .serviceConfiguration(
//            S3Configuration.builder()
//                .pathStyleAccessEnabled(true)
//                .build()
//        )
//        .build();
//  }
}
