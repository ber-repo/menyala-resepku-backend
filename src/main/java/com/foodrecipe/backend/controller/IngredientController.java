package com.foodrecipe.backend.controller;


import com.foodrecipe.backend.exception.AlreadyExistsException;
import com.foodrecipe.backend.exception.ResourceNotFoundException;
import com.foodrecipe.backend.model.Ingredient;
import com.foodrecipe.backend.response.ApiResponse;
import com.foodrecipe.backend.service.ingredient.IngredientService;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllIngredient() {
        try {
            List<Ingredient> ingredient = ingredientService.getAllIngredient();
            return ResponseEntity.ok(new ApiResponse(ingredient, "found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(null, "Error: " + e.getMessage()));
        }
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addIngredient(@RequestBody Ingredient name){
        try {
            Ingredient theIngredient = ingredientService.addIngredient(name);
            return ResponseEntity.ok(new ApiResponse(theIngredient, "Success"));
        }catch(AlreadyExistsException e){
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(null, e.getMessage()));
        }

    }


    @GetMapping("id/{id}")
    public ResponseEntity<ApiResponse> getIngredientById(@PathVariable Integer id){
        try {
            Ingredient theIngredient = ingredientService.getIngredientById(id);
            return ResponseEntity.ok(new ApiResponse(theIngredient, "Found"));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, e.getMessage()));
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse> getIngredientByName(@PathVariable String name){
        try {
            Ingredient theIngredient = ingredientService.getIngredientByName(name);
            return ResponseEntity.ok(new ApiResponse(theIngredient, "Found"));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteIngredient(@PathVariable Integer id){
        try {
            ingredientService.deleteIngredient(id);
            return ResponseEntity.ok(new ApiResponse(null, "Delete success"));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, e.getMessage()));
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateIngredient(@PathVariable Integer id, @RequestBody Ingredient ingredient){
        try {
            Ingredient updateIngredient = ingredientService.updateIngredient(ingredient, id);
            return ResponseEntity.ok(new ApiResponse(updateIngredient, "Update success"));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, e.getMessage()));
        }
    }
    
}
