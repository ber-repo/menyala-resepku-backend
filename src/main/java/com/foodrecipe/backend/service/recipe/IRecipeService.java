package com.foodrecipe.backend.service.recipe;

import com.foodrecipe.backend.model.Recipe;
import com.foodrecipe.backend.request.AddRecipeRequest;

import java.util.List;

public interface IRecipeService {
    Recipe addRecipe(AddRecipeRequest recipe);
    Recipe getRecipeById(Integer id);
    void deleteRecipeById(Integer id);
    void updateRecipe(Recipe recipe, Integer id);
    List<Recipe> getAllRecipe();
    List<Recipe> getRecipeByName(String name);
    List<Recipe> getRecipeByIsFavorite(Boolean favorite);
}
