package com.foodrecipe.backend.request;

import com.foodrecipe.backend.model.Category;
import com.foodrecipe.backend.model.Image;
import com.foodrecipe.backend.model.User;

import java.util.List;

public class AddRecipeRequest {
    private Integer id;
    private String recipeName;
    private String recipeDescription;
    private Boolean isFavorite = false;
    private Category category;
    private User user;
    private Integer userId;


    public AddRecipeRequest() {
    }

    public AddRecipeRequest(String recipeName, Boolean isFavorite, String recipeDescription, Category category, User user) {
        this.recipeName = recipeName;
        this.isFavorite = isFavorite;
        this.recipeDescription = recipeDescription;
        this.category = category;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


}