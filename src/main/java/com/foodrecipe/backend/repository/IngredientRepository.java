package com.foodrecipe.backend.repository;

import com.foodrecipe.backend.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    Ingredient findByIngredient(String name);
    boolean existsByIngredient(String ingredient);
}
