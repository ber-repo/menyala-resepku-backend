package com.foodrecipe.backend.service.image;

import com.foodrecipe.backend.dto.ImageDto;
import com.foodrecipe.backend.exception.ResourceNotFoundException;
import com.foodrecipe.backend.model.Image;
import com.foodrecipe.backend.model.Recipe;
import com.foodrecipe.backend.repository.ImageRepository;
import com.foodrecipe.backend.service.recipe.IRecipeService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService implements IImageService{
    private final ImageRepository imageRepository;
    private final IRecipeService recipeService;

    public ImageService(ImageRepository imageRepository, IRecipeService recipeService) {
        this.imageRepository = imageRepository;
        this.recipeService = recipeService;
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
}
