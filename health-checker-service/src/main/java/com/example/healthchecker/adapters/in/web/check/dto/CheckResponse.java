package com.example.healthchecker.adapters.in.web.check.dto;

import com.example.healthchecker.domain.check.JobStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "A health check job and its current results")
public record CheckResponse(
        @Schema(description = "Job id", example = "02ae85d4-56e1-43b0-90fa-27631511647a") String id,
        @Schema(description = "Job status") JobStatus status,
        @Schema(description = "Results collected so far") List<CheckResultResponse> results) {
}
