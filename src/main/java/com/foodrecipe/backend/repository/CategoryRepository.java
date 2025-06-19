package com.foodrecipe.backend.repository;

import com.foodrecipe.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByName(String name);
    boolean existsByName(String name);
}