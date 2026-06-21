package com.example.sqlite.adapters.in.web.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Payload to create a user")
public record CreateUserRequest(
        @NotBlank @Schema(description = "User name", example = "Ioshi") String name,
        @NotBlank @Email @Schema(description = "User email", example = "rafa.ioshi@gmail.com") String email) {
}
