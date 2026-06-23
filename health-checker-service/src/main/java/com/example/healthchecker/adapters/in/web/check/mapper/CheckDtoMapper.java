package com.example.healthchecker.adapters.in.web.check.mapper;

import com.example.healthchecker.adapters.in.web.check.dto.CheckResponse;
import com.example.healthchecker.adapters.in.web.check.dto.CheckResultResponse;
import com.example.healthchecker.domain.check.CheckResult;
import com.example.healthchecker.domain.check.Job;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CheckDtoMapper {

    CheckResultResponse toResponse(CheckResult result);

    List<CheckResultResponse> toResponseList(List<CheckResult> results);

    CheckResponse toResponse(Job job);
}
