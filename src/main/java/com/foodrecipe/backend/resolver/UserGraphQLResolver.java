package com.foodrecipe.backend.resolver;

import com.foodrecipe.backend.controller.AuthController;
import com.foodrecipe.backend.model.User;
import com.foodrecipe.backend.dto.UserLoginDto;
import com.foodrecipe.backend.request.CreateUserRequest;
import com.foodrecipe.backend.request.LoginRequest;
import com.foodrecipe.backend.response.ApiResponse;
import com.foodrecipe.backend.response.JwtResponse;
import com.foodrecipe.backend.service.user.UserService;
import com.foodrecipe.backend.exception.AlreadyExistsException;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import graphql.GraphQLException;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Controller
public class UserGraphQLResolver {
    private final UserService userService;
    private final AuthController authController;

    public UserGraphQLResolver(UserService userService, AuthController authController) {
        this.userService = userService;
        this.authController = authController;
    }

    @MutationMapping
    public User signUp(@Argument String userName, @Argument String email, @Argument String password) {
        try {
            CreateUserRequest request = new CreateUserRequest(userName, email, password);
            return userService.createUser(request);
        } catch (AlreadyExistsException e) {
            throw new GraphQLException("Email already exists: " + email);
        }
    }

    @MutationMapping
    public UserLoginDto logIn(@Argument String email, @Argument String password) {
        LoginRequest request = new LoginRequest(email, password);
        ResponseEntity<ApiResponse> response = authController.login(request);

        if (response.getStatusCode().is2xxSuccessful()) {
            JwtResponse jwtResponse = (JwtResponse) response.getBody().getData();
            return new UserLoginDto(jwtResponse.getId(), email, jwtResponse.getToken());
        } else {
            throw new GraphQLException("Login failed: " + response.getBody().getMessage());
        }
    }

    @QueryMapping
    public User userById(@Argument Integer id) {
        return userService.getUserById(id);
    }
}
