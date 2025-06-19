package com.foodrecipe.backend.service.user;

import com.foodrecipe.backend.dto.RecipeDto;
import com.foodrecipe.backend.dto.UserDto;
import com.foodrecipe.backend.model.Recipe;
import com.foodrecipe.backend.model.User;
import com.foodrecipe.backend.request.CreateUserRequest;
import com.foodrecipe.backend.request.UserUpdateRequest;

import java.util.List;

public interface IUserService {
    User getUserById(Integer userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Integer userId);
    void deleteUser(Integer userId);

    List<RecipeDto> getConvertedRecipes(List<Recipe> recipes);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}
