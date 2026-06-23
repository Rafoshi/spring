package com.example.healthchecker.application.portout.check;

import com.example.healthchecker.domain.check.Job;

import java.util.Optional;

public interface JobRepositoryPort {

    void save(Job job);

    Optional<Job> findById(String id);
}
