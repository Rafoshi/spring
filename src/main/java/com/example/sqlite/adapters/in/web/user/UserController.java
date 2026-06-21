package com.example.sqlite.adapters.in.web.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sqlite.adapters.in.web.user.dto.CreateUserRequest;
import com.example.sqlite.adapters.in.web.user.dto.UpdateUserRequest;
import com.example.sqlite.adapters.in.web.user.dto.UserResponse;
import com.example.sqlite.adapters.in.web.user.mapper.UserDtoMapper;
import com.example.sqlite.application.portin.user.CreateUserUseCase;
import com.example.sqlite.application.portin.user.DeleteUserUseCase;
import com.example.sqlite.application.portin.user.ListUsersUseCase;
import com.example.sqlite.application.portin.user.UpdateUserUseCase;
import com.example.sqlite.domain.user.User;

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
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
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

    @Operation(summary = "Update a user")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "400", description = "Email format is invalid",
            content = @Content(schema = @Schema(type = "string")))
    @ApiResponse(responseCode = "404", description = "User not found",
            content = @Content(schema = @Schema(type = "string")))
    @ApiResponse(responseCode = "409", description = "Email is already in use",
            content = @Content(schema = @Schema(type = "string")))
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        User updated = updateUserUseCase.execute(id, userDtoMapper.toDomain(request));
        return ResponseEntity.ok(userDtoMapper.toResponse(updated));
    }

    @Operation(summary = "Delete a user")
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    @ApiResponse(responseCode = "404", description = "User not found",
            content = @Content(schema = @Schema(type = "string")))
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        deleteUserUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
