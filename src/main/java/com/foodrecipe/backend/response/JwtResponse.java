package com.foodrecipe.backend.response;

public class JwtResponse {
    private Integer id;
    private String token;

    public JwtResponse() {
    }

    public JwtResponse(Integer id, String jwt) {
        this.id = id;
        this.token = jwt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
