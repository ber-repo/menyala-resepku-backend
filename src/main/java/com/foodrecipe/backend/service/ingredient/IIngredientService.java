package com.foodrecipe.backend.service.ingredient;

import com.foodrecipe.backend.model.Ingredient;
import com.foodrecipe.backend.model.Recipe;

import java.util.List;

public interface IIngredientService {
    Ingredient getIngredientById(Integer id);
    Ingredient getIngredientByName(String name);
    List<Ingredient> getAllIngredient();
    Ingredient addIngredient(Ingredient ingredient);

    Ingredient save(Ingredient ingredient);

    Ingredient updateIngredient(Ingredient ingredient, Integer id);
    void deleteIngredient(Integer id);

}
