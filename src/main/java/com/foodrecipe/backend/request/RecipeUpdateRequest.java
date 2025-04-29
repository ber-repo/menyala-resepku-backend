package com.foodrecipe.backend.request;

import com.foodrecipe.backend.model.Ingredient;
import com.foodrecipe.backend.model.Step;

import java.util.List;

public class RecipeUpdateRequest {
    private Integer id;
    private String recipeName;
    private String recipeDescription;
    private Boolean isFavorite;
    private List<Ingredient> ingredients;
    private List<Step> steps;

    public RecipeUpdateRequest() {
    }

    public RecipeUpdateRequest(String recipeName, String recipeDescription, Boolean isFavorite,
                               List<Ingredient> ingredients, List<Step> steps) {
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.isFavorite = isFavorite;
        this.ingredients = ingredients;
        this.steps = steps;
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
}


