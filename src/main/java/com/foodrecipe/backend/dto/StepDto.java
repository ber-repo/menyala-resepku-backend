package com.foodrecipe.backend.dto;

public class StepDto {
    private Integer id;
    private String stepDescription;

    public StepDto() {
    }

    public StepDto(String stepDescription) {
        this.stepDescription = stepDescription;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }
}
