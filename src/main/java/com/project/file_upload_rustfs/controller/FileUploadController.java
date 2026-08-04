package com.project.file_upload_rustfs.controller;

import com.project.file_upload_rustfs.model.UploadedResultRustfsDTO;
import com.project.file_upload_rustfs.service.FileStorageService;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {
  private final FileStorageService fileStorageService;

  public FileUploadController(
      FileStorageService fileStorageService
  ) {
    this.fileStorageService = fileStorageService;
  }

  @PostMapping(
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<UploadedResultRustfsDTO> upload(
      @RequestParam("file") MultipartFile file
  ) throws IOException {
    UploadedResultRustfsDTO resultRustfsDTO = fileStorageService.upload(file);
    return ResponseEntity.ok(resultRustfsDTO);
  }
}
