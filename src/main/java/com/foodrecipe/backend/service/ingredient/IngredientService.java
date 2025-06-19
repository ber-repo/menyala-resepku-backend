package com.foodrecipe.backend.service.ingredient;

import com.foodrecipe.backend.exception.AlreadyExistsException;
import com.foodrecipe.backend.exception.ResourceNotFoundException;
import com.foodrecipe.backend.model.Ingredient;
import com.foodrecipe.backend.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class IngredientService implements IIngredientService{
    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Ingredient getIngredientById(Integer id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found!"));
    }

    @Override
    public Ingredient getIngredientByName(String name) {
        return Optional.ofNullable(ingredientRepository.findByIngredientName(name))
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found: " + name));
    }


    @Override
    public List<Ingredient> getAllIngredient() {
        return ingredientRepository.findAll();
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        return Optional.of(ingredient)
                .filter(i -> !ingredientRepository.existsByIngredientName(i.getIngredientName()))
                .map(ingredientRepository::save)
                .orElseThrow(() -> new AlreadyExistsException(ingredient.getIngredientName() + " already exists"));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        return addIngredient(ingredient);
    }


    @Override
    public Ingredient updateIngredient(Ingredient ingredient, Integer id) {
        Ingredient existing = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient with id " + id + " not found!"));

        existing.setIngredientName(ingredient.getIngredientName());
        existing.setRecipe(ingredient.getRecipe());
        return ingredientRepository.save(existing);
    }


    @Override
    public void deleteIngredient(Integer id) {
        ingredientRepository.findById(id)
                .ifPresentOrElse(ingredientRepository::delete, () -> {
                    throw new ResourceNotFoundException("Ingredient not found!");
                });
    }

}
