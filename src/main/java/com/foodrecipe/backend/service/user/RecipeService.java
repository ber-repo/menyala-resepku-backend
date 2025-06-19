package com.foodrecipe.backend.service.recipe;

import com.foodrecipe.backend.dto.ImageDto;
import com.foodrecipe.backend.dto.IngredientDto;
import com.foodrecipe.backend.dto.RecipeDto;
import com.foodrecipe.backend.dto.StepDto;
import com.foodrecipe.backend.exception.RecipeNotFoundException;
import com.foodrecipe.backend.exception.ResourceNotFoundException;
import com.foodrecipe.backend.model.*;
import com.foodrecipe.backend.repository.*;
import com.foodrecipe.backend.request.AddRecipeRequest;
import com.foodrecipe.backend.request.RecipeUpdateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService implements IRecipeService{
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;
    private final CategoryRepository categoryRepository;
    private final StepRepository stepRepository;
    private final UserRepository userRepository;


    public RecipeService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, ModelMapper modelMapper, ImageRepository imageRepository, CategoryRepository categoryRepository, StepRepository stepRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.modelMapper = modelMapper;
        this.imageRepository = imageRepository;
        this.categoryRepository = categoryRepository;
        this.stepRepository = stepRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Recipe addRecipe(AddRecipeRequest request) {
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });

        request.setCategory(category);

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + request.getUserId()));

        return recipeRepository.save(createRecipe(request, category, user));
    }

    private Recipe createRecipe(AddRecipeRequest request, Category category, User user) {
        return new Recipe(
                request.getRecipeDescription(),
                request.getRecipeName(),
                Optional.ofNullable(request.getIsFavorite()).orElse(false),
                category,
                user
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
                .orElseThrow(()-> new RecipeNotFoundException("Recipe not found!"));
    }


    private Recipe updateExistingRecipe(Recipe existingProduct, RecipeUpdateRequest request) {
        existingProduct.setRecipeName(request.getRecipeName());
        existingProduct.setRecipeDescription(request.getRecipeDescription());
        existingProduct.setIsFavorite(request.getIsFavorite());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return  existingProduct;

    }

    @Override
    public List<Recipe> getAllRecipe() {
        return recipeRepository.findAll();
    }

    @Override
    public List<Recipe> searchRecipesByName(String keyword) {
        if(keyword == null || keyword.isEmpty()) {
            return new ArrayList<>();
        }

        List<Recipe> recipes = recipeRepository.findByRecipeNameContainingIgnoreCase(keyword);
        if(recipes.isEmpty()) {
            throw new ResourceNotFoundException("No recipes found with name: " + keyword);
        }
        return recipes;
    }

    @Override
    public List<Recipe> getRecipeByIsFavorite(Boolean isFavorite) {
        return recipeRepository.findByIsFavorite(isFavorite);
    }

    @Override
    public Recipe updateToggleFavoriteStatus(Integer id){
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with id: " + id));


        Boolean currentFavorite = recipe.getIsFavorite();
        if (currentFavorite == null) {
            recipe.setIsFavorite(true);
        } else {
            recipe.setIsFavorite(!currentFavorite);
        }

        return recipeRepository.save(recipe);
    }

    @Override
    public List<RecipeDto> getConvertedRecipes(List<Recipe> recipes) {
        return recipes.stream().map(this::convertToDto).toList();
    }

    @Override
    public RecipeDto convertToDto(Recipe recipe){
        RecipeDto recipeDto = modelMapper.map(recipe, RecipeDto.class);


        List<Image> images = imageRepository.findByRecipeId(recipe.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        recipeDto.setImages(imageDtos);


        List<Ingredient> ingredients = ingredientRepository.findByRecipeId(recipe.getId());
        List<IngredientDto> ingredientDtos = ingredients.stream()
                .map(ingredient -> modelMapper.map(ingredient, IngredientDto.class))
                .toList();
        recipeDto.setIngredient(ingredientDtos);


        List<Step> steps = stepRepository.findByRecipeId(recipe.getId());
        List<StepDto> stepDtos = steps.stream()
                .map(step -> modelMapper.map(step, StepDto.class))
                .toList();
        recipeDto.setStep(stepDtos);

        return recipeDto;
    }

    @Override
    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }



}
