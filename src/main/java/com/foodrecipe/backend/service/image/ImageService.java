package com.foodrecipe.backend.service.image;

import com.foodrecipe.backend.dto.ImageDto;
import com.foodrecipe.backend.exception.ResourceNotFoundException;
import com.foodrecipe.backend.model.Image;
import com.foodrecipe.backend.model.Recipe;
import com.foodrecipe.backend.repository.ImageRepository;
import com.foodrecipe.backend.repository.RecipeRepository;
import com.foodrecipe.backend.service.recipe.RecipeService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService implements IImageService{
    private final ImageRepository imageRepository;
    private final RecipeService recipeService;
    private final RecipeRepository recipeRepository;

    public ImageService(ImageRepository imageRepository, RecipeService recipeService, RecipeRepository recipeRepository) {
        this.imageRepository = imageRepository;
        this.recipeService = recipeService;
        this.recipeRepository = recipeRepository;
    }


    @Override
    public Image getImageById(Integer id) {
        return imageRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No image fount with id: " + id));
    }

    @Override
    public void deleteImageById(Integer id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,() -> {
            throw new ResourceNotFoundException("No image fount with id: " + id);
        });
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Integer recipeId) {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        List<ImageDto> savedImageDto = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(file.getBytes());
                image.setRecipe(recipe);

                Image savedImage = imageRepository.save(image);

                String buildDownloadUrl = "/api/v1/images/image/download/" + savedImage.getId();
                savedImage.setDownloadUrl(buildDownloadUrl);

                savedImage = imageRepository.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDto.add(imageDto);

            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return savedImageDto;
    }


    @Override
    public void updateImage(MultipartFile file, Integer imageId) {
        Image image = getImageById(imageId);
        try{
        image.setFileName(file.getOriginalFilename());
        image.setFileName(file.getOriginalFilename());
        image.setImage(file.getBytes());
        imageRepository.save(image);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public void updateImageRecipeByUrl(String downloadUrl, Recipe recipe) {
        Image image = imageRepository.findByDownloadUrl(downloadUrl)
            .orElseThrow(() -> new ResourceNotFoundException("Image not found with url: " + downloadUrl));
        image.setRecipe(recipe);
        imageRepository.save(image);
    }

    @Override
    public List<Image> getImagesByRecipe(Recipe recipe) {
        return imageRepository.findByRecipeId(recipe.getId());
    }

    @Override
    public Image saveImage(MultipartFile file, Integer recipeId) {
        try {
            Image imageEntity = new Image();
            imageEntity.setFileName(file.getOriginalFilename());
            imageEntity.setFileType(file.getContentType());
            imageEntity.setImage(file.getBytes()); // BLOB


            if (recipeId != null) {
                Recipe recipe = recipeRepository.findById(recipeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Recipe not found"));
                imageEntity.setRecipe(recipe);
            }

            return imageRepository.save(imageEntity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save image", e);
        }
    }
}
