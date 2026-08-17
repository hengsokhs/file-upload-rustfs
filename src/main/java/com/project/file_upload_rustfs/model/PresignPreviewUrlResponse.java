package com.project.file_upload_rustfs.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PresignPreviewUrlResponse {
    private String url;
}
