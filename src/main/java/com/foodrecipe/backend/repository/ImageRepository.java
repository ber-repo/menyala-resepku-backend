package com.foodrecipe.backend.repository;

import com.foodrecipe.backend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
