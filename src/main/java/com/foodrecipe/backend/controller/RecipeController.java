package com.foodrecipe.backend.controller;
import com.foodrecipe.backend.exception.ResourceNotFoundException;
import com.foodrecipe.backend.model.Recipe;
import com.foodrecipe.backend.request.AddRecipeRequest;
import com.foodrecipe.backend.request.RecipeUpdateRequest;
import com.foodrecipe.backend.response.ApiResponse;
import com.foodrecipe.backend.service.recipe.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipe();
        return ResponseEntity.ok(new ApiResponse(recipes, "Success"));
    }

    @GetMapping("/recipe/{recipeId}/recipe")
    public ResponseEntity<ApiResponse> getRecipeById(@PathVariable Integer recipeId) {
        try {
            Recipe recipe = recipeService.getRecipeById(recipeId);
            return ResponseEntity.ok(new ApiResponse(recipe, "Success"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, e.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addRecipe(@RequestBody AddRecipeRequest recipe) {
        try {
            Recipe theRecipe = recipeService.addRecipe(recipe);
            return ResponseEntity.ok(new ApiResponse(theRecipe, "Success"));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(null, e.getMessage()));
        }
    }

    @PutMapping("/recipe/{recipeId}/update")
    public ResponseEntity<ApiResponse> updateRecipe(@RequestBody RecipeUpdateRequest request, @PathVariable Integer recipeId) {
        try {
            Recipe theRecipe = recipeService.updateRecipe(request, recipeId);
            return ResponseEntity.ok(new ApiResponse(theRecipe, "Update recipe success"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, e.getMessage()));
        }
    }

    @DeleteMapping("/recipe/{recipeId}/delete")
    public ResponseEntity<ApiResponse> deleteRecipe(@PathVariable Integer recipeId){
        try {
            recipeService.deleteRecipeById(recipeId);
            return ResponseEntity.ok(new ApiResponse(null, "Delete recipe success"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, e.getMessage()));
        }
    }


    @GetMapping("/recipe/{name}/recipe")
    public ResponseEntity<ApiResponse> getRecipeByName(@PathVariable String name){
        try {
            List<Recipe> recipes = recipeService.searchRecipesByName(name);
            return ResponseEntity.ok(new ApiResponse(recipes, "Success"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, e.getMessage()));
        }
    }

    @GetMapping("/recipe/{favorite}/recipe")
    public ResponseEntity<ApiResponse> getRecipeByIsFavorite(@PathVariable Boolean favorite){
        try {
            List<Recipe> recipes = recipeService.getRecipeByIsFavorite(favorite);
            return ResponseEntity.ok(new ApiResponse(recipes, "Success"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, e.getMessage()));
        }
    }

    @PutMapping("/recipes/{id}/favorite")
    public ResponseEntity<ApiResponse> toggleFavoriteStatus(@PathVariable Integer id){
        try {
            Recipe recipe = recipeService.toggleFavoriteStatus(id);
            return ResponseEntity.ok(new ApiResponse(recipe, "Success"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, e.getMessage()));
        }
    }


}
