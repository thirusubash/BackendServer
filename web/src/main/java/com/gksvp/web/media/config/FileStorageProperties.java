package com.gksvp.web.media.config;

import lombok.Getter;


@Getter
public class FileStorageProperties {
    private String uploadDir;

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
