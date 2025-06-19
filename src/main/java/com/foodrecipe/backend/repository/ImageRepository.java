package com.foodrecipe.backend.repository;

import com.foodrecipe.backend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findByRecipeId(Integer id);
    Optional<Image> findByDownloadUrl(String downloadUrl);
}
