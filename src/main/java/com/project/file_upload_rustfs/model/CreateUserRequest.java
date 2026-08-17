package com.project.file_upload_rustfs.model;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateUserRequest {
    private String username;
    private String key;
}
