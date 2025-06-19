package com.foodrecipe.backend.service.recipe;

import com.foodrecipe.backend.dto.RecipeDto;
import com.foodrecipe.backend.model.Recipe;
import com.foodrecipe.backend.model.User;
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
    Recipe updateToggleFavoriteStatus(Integer id);
    List<RecipeDto> getConvertedRecipes(List<Recipe> recipe);
    RecipeDto convertToDto(Recipe recipe);

    Recipe save(Recipe recipe);
}

