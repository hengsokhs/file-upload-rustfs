package com.project.file_upload_rustfs.model;

import lombok.Data;

@Data
public class PresignUploadUrlRequest {
  private String filename;
  private long size;
}
