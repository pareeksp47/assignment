package com.fieldwire.assignment.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "file")
@EnableConfigurationProperties(FileStorageProperties.class)
@Component
public class FileStorageProperties {

    private String uploadDir;
    private Integer thumbWidth;
    private Integer thumbHeight;
    private Integer largeWidth;
    private Integer largeHeight;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public Integer getThumbWidth() {
        return thumbWidth;
    }

    public void setThumbWidth(Integer thumbWidth) {
        this.thumbWidth = thumbWidth;
    }

    public Integer getThumbHeight() {
        return thumbHeight;
    }

    public void setThumbHeight(Integer thumbHeight) {
        this.thumbHeight = thumbHeight;
    }

    public Integer getLargeWidth() {
        return largeWidth;
    }

    public void setLargeWidth(Integer largeWidth) {
        this.largeWidth = largeWidth;
    }

    public Integer getLargeHeight() {
        return largeHeight;
    }

    public void setLargeHeight(Integer largeHeight) {
        this.largeHeight = largeHeight;
    }

}