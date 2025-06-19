package com.foodrecipe.backend.controller;

import com.foodrecipe.backend.exception.ResourceNotFoundException;
import com.foodrecipe.backend.model.Step;
import com.foodrecipe.backend.response.ApiResponse;
import com.foodrecipe.backend.service.step.IStepService;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/step")
public class StepController {

    private final IStepService stepService;

    public StepController(IStepService stepService) {
        this.stepService = stepService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addStep(@RequestBody Step step) {
        try {
            Step createdStep = stepService.addStep(step);
            return ResponseEntity.ok(new ApiResponse(createdStep,"Success"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(null, e.getMessage()));
        }
    }


    @GetMapping("/{id}/step")
    public ResponseEntity<ApiResponse> getStepById(@PathVariable Integer id) {
        try {
            Step step = stepService.getStepById(id);
            return ResponseEntity.ok(new ApiResponse(step,"Success"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, e.getMessage()));
        }
    }


    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<ApiResponse> getStepsByRecipeId(@PathVariable Integer recipeId) {
        try {
            List<Step> steps = stepService.getStepByRecipeId(recipeId);
            return ResponseEntity.ok(new ApiResponse(steps,"Success"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, e.getMessage()));
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateStep(@PathVariable Integer id, @RequestBody Step stepDetails) {
        try {
            Step updatedStep = stepService.updateStep(id, stepDetails);
            return ResponseEntity.ok(new ApiResponse(updatedStep,"Success"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, e.getMessage()));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteStep(@PathVariable Integer id) {
        try {
            stepService.deleteStep(id);
            return ResponseEntity.ok(new ApiResponse("Remove recipe success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, e.getMessage()));
        }
    }
}
