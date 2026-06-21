package com.example.sqlite.adapters.in.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sqlite.adapters.in.web.dto.CreateUserRequest;
import com.example.sqlite.adapters.in.web.dto.UserResponse;
import com.example.sqlite.adapters.in.web.mapper.UserDtoMapper;
import com.example.sqlite.application.portin.CreateUserUseCase;
import com.example.sqlite.application.portin.ListUsersUseCase;
import com.example.sqlite.domain.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Users", description = "User management endpoints")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final ListUsersUseCase listUsersUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UserDtoMapper userDtoMapper;

    @Operation(summary = "List all users")
    @ApiResponse(responseCode = "200", description = "Users returned successfully")
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userDtoMapper.toResponseList(listUsersUseCase.execute());
    }

    @Operation(summary = "Create a user")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @ApiResponse(responseCode = "400", description = "Email format is invalid",
            content = @Content(schema = @Schema(type = "string")))
    @ApiResponse(responseCode = "409", description = "Email is already in use",
            content = @Content(schema = @Schema(type = "string")))
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        User created = createUserUseCase.execute(userDtoMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(userDtoMapper.toResponse(created));
    }
}
