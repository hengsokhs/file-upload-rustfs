package com.project.file_upload_rustfs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UploadedResultRustfsDTO {
  private String key;
  private String eTag;
  private long size;
  private String contentType;
}
