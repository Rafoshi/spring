package com.example.healthchecker.adapters.in.web.check.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Schema(description = "Payload to start a health check job")
public record CreateCheckRequest(

        @NotEmpty
        @Schema(description = "URLs to check", example = "[\"https://example.com\"]")
        List<@NotBlank String> urls) {
}
