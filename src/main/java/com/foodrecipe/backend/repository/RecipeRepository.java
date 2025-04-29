package com.foodrecipe.backend.repository;

import com.foodrecipe.backend.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {


    List<Recipe> findByIsFavorite(Boolean isFavorite);
    List<Recipe> findByRecipeNameContainingIgnoreCase(String keyword);
}
