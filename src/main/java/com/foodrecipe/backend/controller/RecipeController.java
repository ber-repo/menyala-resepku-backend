package com.foodrecipe.backend.controller;
import com.foodrecipe.backend.dto.RecipeDto;
import com.foodrecipe.backend.exception.ResourceNotFoundException;
import com.foodrecipe.backend.model.Recipe;
import com.foodrecipe.backend.model.User;
import com.foodrecipe.backend.request.AddRecipeRequest;
import com.foodrecipe.backend.request.RecipeUpdateRequest;
import com.foodrecipe.backend.response.ApiResponse;
import com.foodrecipe.backend.service.recipe.RecipeService;
import com.foodrecipe.backend.service.user.UserService;
import io.jsonwebtoken.JwtException;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/recipe")
public class RecipeController {
    private final RecipeService recipeService;
    private final UserService userService;


    public RecipeController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipe();
        List<RecipeDto> convertedRecipes = recipeService.getConvertedRecipes(recipes);
        return ResponseEntity.ok(new ApiResponse(convertedRecipes, "Success"));
    }


    @GetMapping("id/{recipeId}")
    public ResponseEntity<ApiResponse> getRecipeById(@PathVariable Integer recipeId) {
        try {
            Recipe recipe = recipeService.getRecipeById(recipeId);
            RecipeDto recipeDto = recipeService.convertToDto(recipe);
            return ResponseEntity.ok(new ApiResponse(recipeDto, "Success"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, e.getMessage()));
        }
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addRecipe(@RequestBody AddRecipeRequest recipe) {
        try {
            User user = userService.getAuthenticatedUser();
            Recipe theRecipe = recipeService.addRecipe(recipe);
            RecipeDto recipeDto = recipeService.convertToDto(theRecipe);
            return ResponseEntity.ok(new ApiResponse(recipeDto, "Add Recipe Success!"));
        } catch (JwtException e){
            return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(null, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(null, e.getMessage()));
        }
    }


    @PutMapping("/recipe/{recipeId}/update")
    public ResponseEntity<ApiResponse> updateRecipe(@RequestBody RecipeUpdateRequest request, @PathVariable Integer recipeId) {
        try {
            Recipe theRecipe = recipeService.updateRecipe(request, recipeId);
            RecipeDto recipeDto = recipeService.convertToDto(theRecipe);
            return ResponseEntity.ok(new ApiResponse(recipeDto, "Update recipe success"));
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


    @GetMapping("name/{name}")
    public ResponseEntity<ApiResponse> getRecipeByName(@PathVariable String name){
        try {
            List<Recipe> recipes = recipeService.searchRecipesByName(name);
            List<RecipeDto> convertedRecipes = recipeService.getConvertedRecipes(recipes);
            return ResponseEntity.ok(new ApiResponse(convertedRecipes, "Success"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, e.getMessage()));
        }
    }


    @GetMapping("/favorite/{favorite}")
    public ResponseEntity<ApiResponse> getRecipeByIsFavorite(@PathVariable Boolean favorite){
        try {
            List<Recipe> recipes = recipeService.getRecipeByIsFavorite(favorite);
            List<RecipeDto> convertedRecipes = recipeService.getConvertedRecipes(recipes);
            return ResponseEntity.ok(new ApiResponse(convertedRecipes, "Success"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, e.getMessage()));
        }
    }


    @PutMapping("/{id}/favorite")
    public ResponseEntity<ApiResponse> updateToggleFavoriteStatus(@PathVariable Integer id){
        try {
            Recipe recipe = recipeService.updateToggleFavoriteStatus(id);
            RecipeDto recipeDto = recipeService.convertToDto(recipe);
            return ResponseEntity.ok(new ApiResponse(recipeDto, "Success"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, e.getMessage()));
        }
    }


}
