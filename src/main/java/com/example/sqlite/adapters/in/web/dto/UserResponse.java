package com.example.sqlite.adapters.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "A user returned by the API")
public record UserResponse(
        @Schema(description = "User id", example = "1") Long id,
        @Schema(description = "User name", example = "Ioshi") String name,
        @Schema(description = "User email", example = "rafa.ioshi@gmail.com") String email) {
}
