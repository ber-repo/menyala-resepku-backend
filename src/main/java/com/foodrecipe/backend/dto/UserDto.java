package com.foodrecipe.backend.dto;

import org.hibernate.annotations.NaturalId;

import java.util.List;

public class UserDto {
    private Integer id;
    private String userName;
    private String email;
    private List<RecipeDto> recipe;

    public UserDto() {
    }

    public UserDto(String userName, String email, List<RecipeDto> recipe) {
        this.userName = userName;
        this.email = email;
        this.recipe = recipe;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<RecipeDto> getRecipe() {
        return recipe;
    }

    public void setRecipe(List<RecipeDto> recipe) {
        this.recipe = recipe;
    }
}
