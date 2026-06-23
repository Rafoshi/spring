package com.example.healthchecker.application.portin.check;

import com.example.healthchecker.domain.check.Job;

import java.util.List;

public interface StartCheckUseCase {

    Job execute(List<String> urls);
}
