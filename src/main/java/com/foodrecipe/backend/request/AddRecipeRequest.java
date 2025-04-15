package com.foodrecipe.backend.request;

import com.foodrecipe.backend.model.Ingredient;
import com.foodrecipe.backend.model.Step;

import java.util.List;

public class AddRecipeRequest {
    private Integer id;
    private String recipeName;
    private String recipeDescription;
    private Boolean isFavorite;
    private Ingredient ingredient;
    private Step step;

    public AddRecipeRequest() {
    }

    public AddRecipeRequest(String recipeName, String recipeDescription, Boolean isFavorite, Ingredient ingredient, Step step) {
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.isFavorite = isFavorite;
        this.ingredient = ingredient;
        this.step = step;
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

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }
}

