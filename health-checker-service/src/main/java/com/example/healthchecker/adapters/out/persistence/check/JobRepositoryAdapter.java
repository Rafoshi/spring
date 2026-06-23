package com.example.healthchecker.adapters.out.persistence.check;

import com.example.healthchecker.application.portout.check.JobRepositoryPort;
import com.example.healthchecker.domain.check.Job;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JobRepositoryAdapter implements JobRepositoryPort {

    private final Map<String, Job> jobs = new ConcurrentHashMap<>();

    @Override
    public void save(Job job) {
        jobs.put(job.getId(), job);
    }

    @Override
    public Optional<Job> findById(String id) {
        return Optional.ofNullable(jobs.get(id));
    }
}
