package com.foodrecipe.backend.request;

public class UserUpdateRequest {
    private String userName;

    public UserUpdateRequest() {
    }

    public UserUpdateRequest(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
