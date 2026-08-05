package com.project.file_upload_rustfs.model;

import lombok.Data;

@Data
public class CreateUploadRequest {
  private String filename;
  private String contentType;
  private long size;
}
