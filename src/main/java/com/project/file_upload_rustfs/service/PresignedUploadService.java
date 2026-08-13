package com.project.file_upload_rustfs.service;

import com.project.file_upload_rustfs.config.S3PresignerConfig;
import com.project.file_upload_rustfs.config.S3Properties;
import com.project.file_upload_rustfs.model.CreateUploadRequest;
import com.project.file_upload_rustfs.model.CreateUploadResponse;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
public class PresignedUploadService {

  private static final Duration EXPIRATION = Duration.ofMinutes(5);

  private static final long MAX_FILE_SIZE = 20L * 1024 * 1024;

  private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
      "image/jpeg",
      "image/jpg",
      "image/png",
      "image/webp",
      "image/gif"
      );

  private final S3Presigner presigner;
  private final S3Properties properties;

  public PresignedUploadService(
      S3Presigner presigner,
      S3Properties properties
  ) {
    this.presigner = presigner;
    this.properties = properties;
  }

  public CreateUploadResponse createUpload(CreateUploadRequest request) {
    validate(request);

    String extension = getExtension(request.getFilename());

    String key = "images/"
        + LocalDate.now(ZoneOffset.UTC)
        + "/"
        + UUID.randomUUID()
        + extension;

    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
        .bucket(properties.getBucket())
        .key(key)
        .build();

    PutObjectPresignRequest presignRequest =
        PutObjectPresignRequest.builder()
            .signatureDuration(EXPIRATION)
            .putObjectRequest(putObjectRequest)
            .build();

    PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);

    Instant expiresAt = Instant.now().plus(EXPIRATION);

    return new CreateUploadResponse(
        key,
        presignedRequest.url().toString(),
        "PUT",
        null,
        expiresAt
    );
  }

  private void validate(CreateUploadRequest request) {
    if (Objects.isNull(request)) {
      throw new IllegalArgumentException("Upload request is required");
    }

    if (!StringUtils.hasText(request.getFilename())) {
      throw new IllegalArgumentException("Filename is required");
    }

//    if (!ALLOWED_CONTENT_TYPES.contains(request.getContentType())) {
//      throw new IllegalArgumentException("Unsupported content type");
//    }
//
//    if (request.getSize() <= 0) {
//      throw new IllegalArgumentException("File must not be empty");
//    }
//
//    if (request.getSize() > MAX_FILE_SIZE) {
//      throw new IllegalArgumentException("File exceeds the 20MB limit");
//    }
  }

  private String getExtension(String filename) {
    String cleanName = StringUtils.cleanPath(filename);

    int dotIndex = cleanName.lastIndexOf(".");

    if (dotIndex < 0) {
      return "";
    }

    String extension = cleanName
        .substring(dotIndex)
        .toLowerCase();

    if(!extension.matches("\\.[a-z0-9]{1,10}")) {
      return "";
    }

    return extension;
  }

  public String createPreviewUrl(String key) {
    GetObjectRequest getObjectRequest = GetObjectRequest.builder()
            .bucket(properties.getBucket())
            .key(key)
            .build();

    GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(15))
            .getObjectRequest(getObjectRequest)
            .build();

    PresignedGetObjectRequest presignedGetObjectRequest = presigner.presignGetObject(presignRequest);

    return presignedGetObjectRequest.url().toString();
  }

}
