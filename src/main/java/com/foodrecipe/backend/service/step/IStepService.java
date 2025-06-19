package com.foodrecipe.backend.service.step;

import com.foodrecipe.backend.model.Step;

import java.util.List;

public interface IStepService {
    Step addStep(Step step);
    Step getStepById(Integer id);
    List<Step> getStepByRecipeId(Integer recipeId);
    Step updateStep(Integer id, Step stepDetails);
    void deleteStep(Integer id);

    Step save(Step step);
}
