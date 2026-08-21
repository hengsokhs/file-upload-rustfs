package com.project.file_upload_rustfs.model.presignedMultipartUpload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompleteResponse {
  private String key;
  private String eTag;
}
