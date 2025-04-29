package com.foodrecipe.backend.dto;

public class ImageDto {
    private Integer imageId;
    private String imageName;
    private String downloadUrl;

    public ImageDto() {
    }

    public ImageDto(String imageName, String downloadUrl) {
        this.imageName = imageName;
        this.downloadUrl = downloadUrl;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}


