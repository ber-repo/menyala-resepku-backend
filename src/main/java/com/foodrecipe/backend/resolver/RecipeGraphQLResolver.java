package com.foodrecipe.backend.resolver;

import com.foodrecipe.backend.exception.AlreadyExistsException;
import com.foodrecipe.backend.exception.ResourceNotFoundException;
import com.foodrecipe.backend.model.*;
import com.foodrecipe.backend.request.CreateUserRequest;
import com.foodrecipe.backend.service.category.CategoryService;
import com.foodrecipe.backend.service.image.ImageService;
import com.foodrecipe.backend.service.ingredient.IngredientService;
import com.foodrecipe.backend.service.recipe.RecipeService;
import com.foodrecipe.backend.service.step.StepService;
import graphql.GraphQLException;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RecipeGraphQLResolver {
    private final RecipeService recipeService;
    private final ImageService imageService;
    private final StepService stepService;
    private final IngredientService ingredientService;
    private final CategoryService categoryService;

    public RecipeGraphQLResolver(RecipeService recipeService, ImageService imageService, StepService stepService, IngredientService ingredientService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
        this.stepService = stepService;
        this.ingredientService = ingredientService;
        this.categoryService = categoryService;
    }

    @QueryMapping
    public List<Recipe> allRecipe() {
        return recipeService.getAllRecipe();
    }

    @QueryMapping
    public Recipe recipeById(@Argument("id") Integer id) {
        return recipeService.getRecipeById(id);
    }

    @QueryMapping
    public Image imageById(@Argument("id") Integer id){
        return imageService.getImageById(id);
    }

    @QueryMapping
    public Ingredient IngredientsById(@Argument("id") Integer id) {
        return ingredientService.getIngredientById(id);
    }

    @QueryMapping
    public Step stepById(@Argument("id") Integer id) {
        return stepService.getStepById(id);
    }

    @MutationMapping
    public Recipe addRecipe(@Argument String recipeName, @Argument List<String> images, @Argument String recipeDescription) {
        Recipe recipe = new Recipe();
        recipe.setRecipeName(recipeName);
        recipe.setRecipeDescription(recipeDescription);

        Recipe savedRecipe = recipeService.save(recipe);

        if (images != null) {
            for (String url : images) {
                imageService.updateImageRecipeByUrl(url, savedRecipe);
            }
        }

        return savedRecipe;
    }

    @MutationMapping
    public Ingredient addIngredient(@Argument String ingredientName, @Argument Integer recipeId) {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientName(ingredientName);
        ingredient.setRecipe(recipe);
        return ingredientService.save(ingredient);
    }

    @MutationMapping
    public Step addStep(@Argument String stepDescription, @Argument Integer recipeId) {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        Step step = new Step();
        step.setStepDescription(stepDescription);
        step.setRecipe(recipe);
        return stepService.save(step);
    }

    @MutationMapping
    public Recipe setRecipeCategory(@Argument Integer recipeId, @Argument String categoryName) {
        Recipe recipe = recipeService.getRecipeById(recipeId);

        Category category;
        try {
            category = categoryService.getCategoryByName(categoryName);
        } catch (ResourceNotFoundException ex) {
            category = new Category();
            category.setName(categoryName);
            category = categoryService.save(category);
        }


        recipe.setCategory(category);
        return recipeService.save(recipe);
    }

    @MutationMapping
    public Recipe setRecipeFavorite(@Argument Integer recipeId, @Argument Boolean isFavorite) {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        recipe.setIsFavorite(isFavorite);
        return recipeService.save(recipe);
    }
}
