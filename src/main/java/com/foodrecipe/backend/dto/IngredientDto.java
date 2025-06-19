package com.foodrecipe.backend.dto;

public class IngredientDto {
    private Integer id;
    private String name;

    public IngredientDto() {
    }

    public IngredientDto(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
