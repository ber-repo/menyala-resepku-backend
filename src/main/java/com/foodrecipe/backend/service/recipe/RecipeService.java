package com.foodrecipe.backend.service.recipe;

import com.foodrecipe.backend.exception.RecipeNotFoundException;
import com.foodrecipe.backend.exception.ResourceNotFoundException;
import com.foodrecipe.backend.model.Image;
import com.foodrecipe.backend.model.Ingredient;
import com.foodrecipe.backend.model.Recipe;
import com.foodrecipe.backend.model.Step;
import com.foodrecipe.backend.repository.IngredientRepository;
import com.foodrecipe.backend.repository.RecipeRepository;
import com.foodrecipe.backend.request.AddRecipeRequest;
import com.foodrecipe.backend.request.RecipeUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService implements IRecipeService{
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;


    public RecipeService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
    }


    @Override
    public Recipe addRecipe(AddRecipeRequest request) {

        List<Ingredient> ingredients = new ArrayList<>();
        if (request.getIngredients() != null) {
            for (Ingredient ingredient : request.getIngredients()) {
                Ingredient savedIngredient = Optional.ofNullable(ingredientRepository.findByIngredient(ingredient.getIngredient()))
                        .orElseGet(() -> ingredientRepository.save(new Ingredient(ingredient.getIngredient())));
                ingredients.add(savedIngredient);
            }
        }

        List<Step> steps = request.getSteps() != null ? request.getSteps() : new ArrayList<>();

        
        Recipe recipe = createRecipe(request, ingredients, steps);

        
        for (Ingredient ing : recipe.getIngredients()) {
            ing.setRecipe(recipe);
        }
        for (Step step : recipe.getSteps()) {
            step.setRecipe(recipe);
        }

        return recipeRepository.save(recipe);


//        Ingredient ingredient = Optional.ofNullable(ingredientRepository.findByIngredient(request.getIngredient().getIngredient()))
//                .orElseGet(() -> {
//                    Ingredient newIngredient = new Ingredient(request.getIngredient().getIngredient());
//                    return ingredientRepository.save(newIngredient);
//                });
//        Recipe recipe = createRecipe(request, List.of(ingredient));
//        for (Ingredient ing : recipe.getIngredient()) {
//            ing.setRecipe(recipe);
//        }
//        return recipeRepository.save(recipe);
    }

    private Recipe createRecipe(AddRecipeRequest request, List<Ingredient> ingredients, List<Step> steps) {
        return new Recipe(
                request.getRecipeName(),
                request.getRecipeDescription(),
                request.getIsFavorite(),
                null, 
                ingredients,
                steps
        );
    }



    @Override
    public Recipe getRecipeById(Integer id) {
        return recipeRepository.findById(id)
                .orElseThrow(()->new RecipeNotFoundException("Recipe not found!"));
    }

    @Override
    public void deleteRecipeById(Integer id) {
        recipeRepository.findById(id)
                .ifPresentOrElse(recipeRepository::delete,
                    () -> { throw new RecipeNotFoundException("Recipe not found!");});
    }

    @Override
    public Recipe updateRecipe(RecipeUpdateRequest request, Integer recipeId) {
        return recipeRepository.findById(recipeId)
                .map(existingRecipe -> updateExistingRecipe(existingRecipe,request))
                .map(recipeRepository :: save)
                .orElseThrow(()-> new RecipeNotFoundException("product not found!"));
    }


    private Recipe updateExistingRecipe(Recipe existingRecipe, RecipeUpdateRequest request) {
        
        existingRecipe.setRecipeName(request.getRecipeName());
        existingRecipe.setRecipeDescription(request.getRecipeDescription());
        existingRecipe.setIsFavorite(request.getIsFavorite());

        
        if (request.getIngredients() != null) {
            
            existingRecipe.getIngredients().clear();
            
            
            for (Ingredient ingredient : request.getIngredients()) {
                Ingredient savedIngredient = ingredientRepository.findByIngredient(ingredient.getIngredient());
                if (savedIngredient == null) {
                    savedIngredient = new Ingredient(ingredient.getIngredient());
                    savedIngredient.setRecipe(existingRecipe);
                } else {
                    savedIngredient.setRecipe(existingRecipe);
                }
                existingRecipe.getIngredients().add(savedIngredient);
            }
        }

        
        if (request.getSteps() != null) {
            
            existingRecipe.getSteps().clear();

            
            for (Step step : request.getSteps()) {
                step.setRecipe(existingRecipe);
                existingRecipe.getSteps().add(step);
            }
        }

        return existingRecipe;
    }

    @Override
    public List<Recipe> getAllRecipe() {
        return recipeRepository.findAll();
    }

    @Override
    public List<Recipe> searchRecipesByName(String keyword) {
        if(keyword != null && !keyword.isEmpty()) {
            List<Recipe> recipes = recipeRepository.findByRecipeNameContainingIgnoreCase(keyword);
            if(recipes.isEmpty()) {
                throw new ResourceNotFoundException("No recipes found with name: " + keyword);
            }
            return recipes;
        }
        return recipeRepository.findByRecipeNameContainingIgnoreCase(keyword);
    }

    @Override
    public List<Recipe> getRecipeByIsFavorite(Boolean isFavorite) {
        return recipeRepository.findByIsFavorite(isFavorite);
    }

    @Override
    public Recipe toggleFavoriteStatus(Integer id){
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with id: " + id));

        recipe.setIsFavorite(!recipe.getIsFavorite());

        return recipeRepository.save(recipe);
    }
}
