package com.example.healthchecker.adapters.in.web.check;

import com.example.healthchecker.adapters.in.web.check.dto.CheckResponse;
import com.example.healthchecker.adapters.in.web.check.dto.CreateCheckRequest;
import com.example.healthchecker.adapters.in.web.check.mapper.CheckDtoMapper;
import com.example.healthchecker.application.portin.check.GetCheckUseCase;
import com.example.healthchecker.application.portin.check.StartCheckUseCase;
import com.example.healthchecker.domain.check.Job;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Checks", description = "Health check job endpoints")
@RestController
@RequestMapping("/checks")
@RequiredArgsConstructor
public class CheckController {

    private final StartCheckUseCase startCheckUseCase;
    private final GetCheckUseCase getCheckUseCase;
    private final CheckDtoMapper checkDtoMapper;

    @Operation(summary = "Start a health check job")
    @ApiResponse(responseCode = "202", description = "Job accepted and started")
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CheckResponse start(@Valid @RequestBody CreateCheckRequest request) {
        Job job = startCheckUseCase.execute(request.urls());
        return checkDtoMapper.toResponse(job);
    }

    @Operation(summary = "Get the status/results of a health check job")
    @ApiResponse(responseCode = "200", description = "Job found")
    @ApiResponse(responseCode = "404", description = "Job not found",
            content = @Content(schema = @Schema(type = "string")))
    @GetMapping("/{id}")
    public CheckResponse get(@PathVariable String id) {
        Job job = getCheckUseCase.execute(id);
        return checkDtoMapper.toResponse(job);
    }
}
