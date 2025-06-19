package com.foodrecipe.backend.repository;

import com.foodrecipe.backend.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    Ingredient findByIngredientName(String ingredientName);
    boolean existsByIngredientName(String ingredientName);

    List<Ingredient> findByRecipeId(Integer id);
}
