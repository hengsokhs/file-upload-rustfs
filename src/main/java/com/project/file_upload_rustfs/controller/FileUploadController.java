package com.project.file_upload_rustfs.controller;

import com.project.file_upload_rustfs.model.CreateUploadRequest;
import com.project.file_upload_rustfs.model.CreateUploadResponse;
import com.project.file_upload_rustfs.model.UploadedResultRustfsDTO;
import com.project.file_upload_rustfs.service.FileStorageService;
import com.project.file_upload_rustfs.service.PresignedUploadService;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/s3")
public class FileUploadController {
  private final FileStorageService fileStorageService;
  private final PresignedUploadService presignedUploadService;

  public FileUploadController(
      FileStorageService fileStorageService,
      PresignedUploadService presignedUploadService
  ) {
    this.fileStorageService = fileStorageService;
    this.presignedUploadService = presignedUploadService;
  }

  @PostMapping(
      path = "/upload",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<UploadedResultRustfsDTO> upload(
      @RequestParam("file") MultipartFile file
  ) throws IOException {
    UploadedResultRustfsDTO resultRustfsDTO = fileStorageService.upload(file);
    return ResponseEntity.ok(resultRustfsDTO);
  }

  @GetMapping("/preview")
  public String preview(@RequestParam String key) {
    return fileStorageService.createViewUrl(key);
  }

  @PostMapping("/upload/presign")
  @ResponseStatus(HttpStatus.CREATED)
  public CreateUploadResponse createUpload(@RequestBody CreateUploadRequest request) {
    return presignedUploadService.createUpload(request);
  }
}
