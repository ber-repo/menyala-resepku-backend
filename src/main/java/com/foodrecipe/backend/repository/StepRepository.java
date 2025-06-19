package com.foodrecipe.backend.repository;

import com.foodrecipe.backend.model.Ingredient;
import com.foodrecipe.backend.model.Step;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StepRepository extends JpaRepository<Step, Integer> {
    List<Step> findByRecipeId(Integer id);
}
