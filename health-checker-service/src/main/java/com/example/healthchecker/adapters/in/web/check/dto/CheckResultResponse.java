package com.example.healthchecker.adapters.in.web.check.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Result of checking a single URL")
public record CheckResultResponse(
        @Schema(description = "Checked URL", example = "https://example.com") String url,
        @Schema(description = "Whether the check succeeded") boolean success,
        @Schema(description = "HTTP status code returned, if any") Integer statusCode,
        @Schema(description = "Duration of the check in milliseconds") long durationMillis,
        @Schema(description = "Error message, if the check failed") String error) {
}
