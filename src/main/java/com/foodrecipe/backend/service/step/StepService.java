package com.foodrecipe.backend.service.step;

import com.foodrecipe.backend.exception.ResourceNotFoundException;
import com.foodrecipe.backend.model.Recipe;
import com.foodrecipe.backend.model.Step;
import com.foodrecipe.backend.repository.RecipeRepository;
import com.foodrecipe.backend.repository.StepRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StepService implements IStepService{
    private final StepRepository stepRepository;
    private final RecipeRepository recipeRepository;

    public StepService(StepRepository stepRepository, RecipeRepository recipeRepository) {
        this.stepRepository = stepRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Step addStep(Step step) {
        Recipe recipe = recipeRepository.findById(step.getRecipe().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found!"));

        step.setRecipe(recipe);
        return stepRepository.save(step);
    }

    @Override
    public Step getStepById(Integer id) {
        return stepRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Step not found!"));
    }

    @Override
    public List<Step> getStepByRecipeId(Integer recipeId) {
        recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found!"));

        return stepRepository.findByRecipeId(recipeId);
    }

    @Override
    public Step updateStep(Integer id, Step stepDetails) {
        Step step = stepRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Step not found!"));

        step.setStepDescription(stepDetails.getStepDescription());

        return stepRepository.save(step);
    }

    @Override
    public void deleteStep(Integer id) {
        stepRepository.findById(id)
                .ifPresentOrElse(stepRepository::delete, () -> {
                    throw new ResourceNotFoundException("Step not found!");
                });
    }

    @Override
    public Step save(Step step) {
        return addStep(step);
    }
}
