package com.project.file_upload_rustfs.model.presignedMultipartUpload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StartRequest {
  private String fileName;
  private String contentType;
  private long fileSize;
}
