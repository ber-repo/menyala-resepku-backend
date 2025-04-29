package com.foodrecipe.backend.request;

import com.foodrecipe.backend.model.Ingredient;
import com.foodrecipe.backend.model.Step;

import java.util.List;

public class AddRecipeRequest {
    private Integer id;
    private String recipeName;
    private String recipeDescription;
    private Boolean isFavorite;
    private List<Ingredient> ingredients;  
    private List<Step> steps;  

    public AddRecipeRequest() {
    }

    public AddRecipeRequest(String recipeName, Boolean isFavorite, String recipeDescription, List<Ingredient> ingredients, List<Step> steps) {
        this.recipeName = recipeName;
        this.isFavorite = isFavorite;
        this.recipeDescription = recipeDescription;
        this.ingredients = ingredients;
        this.steps = steps;
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
