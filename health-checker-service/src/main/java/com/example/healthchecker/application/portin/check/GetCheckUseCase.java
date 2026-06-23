package com.example.healthchecker.application.portin.check;

import com.example.healthchecker.domain.check.Job;

public interface GetCheckUseCase {

    Job execute(String id);
}
