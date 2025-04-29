package com.foodrecipe.backend.service.ingredient;

import com.foodrecipe.backend.model.Ingredient;

import java.util.List;

public interface IIngredientService {
    Ingredient getIngredientById(Integer id);
    Ingredient getIngredientByIngredient(String ingredient);
    List<Ingredient> getAllIngredient();
    Ingredient addIngredient(Ingredient ingredient);
    Ingredient updateIngredient(Ingredient ingredient, Integer id);
    void deleteIngredient(Integer id);
}
