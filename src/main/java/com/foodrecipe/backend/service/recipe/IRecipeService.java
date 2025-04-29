package com.foodrecipe.backend.service.recipe;

import com.foodrecipe.backend.model.Recipe;
import com.foodrecipe.backend.request.AddRecipeRequest;
import com.foodrecipe.backend.request.RecipeUpdateRequest;

import java.util.List;

public interface IRecipeService {
    Recipe addRecipe(AddRecipeRequest recipe);
    Recipe getRecipeById(Integer id);
    void deleteRecipeById(Integer id);
    Recipe updateRecipe(RecipeUpdateRequest recipe, Integer id);
    List<Recipe> getAllRecipe();

    List<Recipe> searchRecipesByName(String keyword);
    List<Recipe> getRecipeByIsFavorite(Boolean favorite);
    Recipe toggleFavoriteStatus(Integer id);
}

