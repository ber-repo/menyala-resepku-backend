package com.foodrecipe.backend.service.recipe;

import com.foodrecipe.backend.exception.RecipeNotFoundException;
import com.foodrecipe.backend.model.Ingredient;
import com.foodrecipe.backend.model.Recipe;
import com.foodrecipe.backend.repository.IngredientRepository;
import com.foodrecipe.backend.repository.RecipeRepository;
import com.foodrecipe.backend.request.AddRecipeRequest;
import org.springframework.stereotype.Service;

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
    public Recipe addRecipe(AddRecipeRequest request){

        Ingredient ingredient = Optional.ofNullable(ingredientRepository.findByIngredient(request.getIngredient().getIngredient()))
                .orElseGet(() -> {
                    Ingredient newIngredient = new Ingredient(request.getIngredient().getIngredient());
                    return ingredientRepository.save(newIngredient);
                });


        Recipe recipe = createRecipe(request, List.of(ingredient));


        for (Ingredient ing : recipe.getIngredient()) {
            ing.setRecipe(recipe);
        }


        return recipeRepository.save(recipe);
    }

    private Recipe createRecipe(AddRecipeRequest request, List<Ingredient> ingredients) {
        return new Recipe(
                request.getRecipeName(),
                request.getRecipeDescription(),
                request.getIsFavorite(),
                null, // Images
                ingredients,
                request.getStep() != null ? List.of(request.getStep()) : null
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
    public void updateRecipe(Recipe recipe, Integer id) {

    }

    @Override
    public List<Recipe> getAllRecipe() {
        return recipeRepository.findAll();
    }

    @Override
    public List<Recipe> getRecipeByName(String recipeName) {
        return recipeRepository.findByRecipeName(recipeName);
    }

    @Override
    public List<Recipe> getRecipeByIsFavorite(Boolean isFavorite) {
        return  recipeRepository.findByFavorite(isFavorite);
    }
}
