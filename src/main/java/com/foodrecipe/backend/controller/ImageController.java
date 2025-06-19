package com.foodrecipe.backend.controller;

import com.foodrecipe.backend.dto.ImageDto;
import com.foodrecipe.backend.exception.ResourceNotFoundException;
import com.foodrecipe.backend.model.Image;
import com.foodrecipe.backend.model.Recipe;
import com.foodrecipe.backend.response.ApiResponse;
import com.foodrecipe.backend.service.image.IImageService;
import com.foodrecipe.backend.service.image.ImageService;
import org.springframework.core.codec.ByteArrayEncoder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final IImageService imageService;

    public ImageController(IImageService imageService) {
        this.imageService = imageService;
    }

//    @PostMapping("/upload")
//    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files,
//                                                  @RequestParam Integer recipeId) {
//        try {
//            List<ImageDto> imageDtos = imageService.saveImages(files, recipeId);
//            return ResponseEntity.ok(new ApiResponse(imageDtos, "Upload success"));
//        } catch (Exception e){
//            return ResponseEntity
//                    .status(INTERNAL_SERVER_ERROR)
//                    .body(new ApiResponse(e.getMessage(), "Upload failed"));
//        }
//    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "recipeId", required = false) Integer recipeId
    ) {
        Image image = imageService.saveImage(file, recipeId);

        // Set downloadUrl setelah image punya ID
        String downloadUrl = "/api/v1/images/download/" + image.getId();
        image.setDownloadUrl(downloadUrl);
        imageService.save(image); // update image dengan downloadUrl

        return ResponseEntity.ok(Map.of("downloadUrl", downloadUrl));
    }

//    @GetMapping("image/download/{imageId}")
//    public ResponseEntity<Resource> downloadImage(@PathVariable Integer imageId) {
//        Image image = imageService.getImageById(imageId);
//        ByteArrayResource resource = new ByteArrayResource(image.getImage());
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(image.getFileType()))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
//                .contentLength(image.getImage().length)
//                .body(resource);
//    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable Integer id) {
        Image image = imageService.getImageById(id);
        return ResponseEntity.ok()
                .header("Content-Type", image.getFileType())
                .body(image.getImage());
    }

    @PutMapping("image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable int imageId, @RequestBody MultipartFile file){
        try{
            Image image = imageService.getImageById(imageId);
            if(image != null){
                imageService.updateImage(file, imageId);
                return ResponseEntity.ok(new ApiResponse(null, "Update success!"));
            } else {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, "Image not found"));
            }
        } catch(ResourceNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(INTERNAL_SERVER_ERROR, "Update failed"));
        }
    }

    @DeleteMapping("image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable int imageId) {
        try{
            Image image = imageService.getImageById(imageId);
            if(image != null){
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse(null, "Delete success!"));
            } else {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, "Image not found"));
            }
        } catch(ResourceNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(INTERNAL_SERVER_ERROR, "Delete failed"));
        }
    }

}
