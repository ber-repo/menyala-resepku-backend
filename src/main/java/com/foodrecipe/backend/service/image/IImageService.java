package com.foodrecipe.backend.service.image;

import com.foodrecipe.backend.dto.ImageDto;
import com.foodrecipe.backend.model.Image;
import com.foodrecipe.backend.model.Recipe;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Integer id);
    void deleteImageById(Integer id);
    List<ImageDto> saveImages(List<MultipartFile> files, Integer recipeId);
    void updateImage(MultipartFile file, Integer imageId);

    Image save(Image image);

    void updateImageRecipeByUrl(String downloadUrl, Recipe recipe);

    List<Image> getImagesByRecipe(Recipe recipe);

    Image saveImage(MultipartFile file, Integer recipeId);
}
