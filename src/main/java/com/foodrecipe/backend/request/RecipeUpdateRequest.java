package com.foodrecipe.backend.request;

import com.foodrecipe.backend.model.Category;

public class RecipeUpdateRequest {
    private Integer id;
    private String recipeName;
    private String recipeDescription;
    private Boolean isFavorite = false;
    private Category category;

    public RecipeUpdateRequest() {
    }

    public RecipeUpdateRequest(String recipeName, Category category, Boolean isFavorite, String recipeDescription) {
        this.recipeName = recipeName;
        this.category = category;
        this.isFavorite = isFavorite;
        this.recipeDescription = recipeDescription;
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
}


