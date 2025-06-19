package com.foodrecipe.backend.service.category;


import com.foodrecipe.backend.model.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    Category getCategoryById(Integer id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, Integer id);
    void deleteCategoryById(Integer id);

    Category save(Category category);
}
