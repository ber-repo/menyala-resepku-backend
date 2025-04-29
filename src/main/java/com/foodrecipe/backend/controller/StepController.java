package com.foodrecipe.backend.controller;

import com.foodrecipe.backend.model.Step;
import com.foodrecipe.backend.service.step.IStepService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/step")
public class StepController {

    private final IStepService stepService;

    public StepController(IStepService stepService) {
        this.stepService = stepService;
    }

    @PostMapping("/add")
    public ResponseEntity<Step> addStep(@RequestBody Step step) {
        Step createdStep = stepService.addStep(step);
        return ResponseEntity.ok(createdStep);
    }

    @GetMapping("/step/{id}/step")
    public ResponseEntity<Step> getStepById(@PathVariable Integer id) {
        Step step = stepService.getStepById(id);
        return ResponseEntity.ok(step);
    }

    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<List<Step>> getStepsByRecipeId(@PathVariable Integer recipeId) {
        List<Step> steps = stepService.getStepByRecipeId(recipeId);
        return ResponseEntity.ok(steps);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Step> updateStep(@PathVariable Integer id, @RequestBody Step stepDetails) {
        Step updatedStep = stepService.updateStep(id, stepDetails);
        return ResponseEntity.ok(updatedStep);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStep(@PathVariable Integer id) {
        stepService.deleteStep(id);
        return ResponseEntity.noContent().build();
    }
}
