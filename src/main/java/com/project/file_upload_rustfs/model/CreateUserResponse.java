package com.project.file_upload_rustfs.model;

import lombok.Data;

@Data
public class CreateUserResponse {
    private Long id;
    private String username;
    private String key;
}
