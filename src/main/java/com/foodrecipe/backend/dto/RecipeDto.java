package com.foodrecipe.backend.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.foodrecipe.backend.model.Category;
import com.foodrecipe.backend.model.Image;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.util.List;

public class RecipeDto {
    private Integer id;
    private String recipeName;
    private String recipeDescription;
    private Boolean isFavorite;
    private Category category;
    private List<ImageDto> images;
    private List<IngredientDto> ingredient;
    private List<StepDto> step;


    public RecipeDto() {
    }

    public RecipeDto(String recipeName, String recipeDescription, Boolean isFavorite, Category category, List<ImageDto> images, List<IngredientDto> ingredient, List<StepDto> step) {
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.isFavorite = isFavorite;
        this.category = category;
        this.images = images;
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

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<IngredientDto> getIngredient() {
        return ingredient;
    }

    public void setIngredient(List<IngredientDto> ingredient) {
        this.ingredient = ingredient;
    }

    public List<ImageDto> getImages() {
        return images;
    }

    public void setImages(List<ImageDto> images) {
        this.images = images;
    }

    public List<StepDto> getStep() {
        return step;
    }

    public void setStep(List<StepDto> step) {
        this.step = step;
    }
}
