package com.example.sqlite.adapters.in.web.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "A page of results")
public record PagedResponse<T>(
        @Schema(description = "Page content") List<T> content,
        @Schema(description = "Current page number (0-based)", example = "0") int page,
        @Schema(description = "Page size", example = "20") int size,
        @Schema(description = "Total number of elements", example = "42") long totalElements,
        @Schema(description = "Total number of pages", example = "3") int totalPages) {
}
