package com.example.healthchecker.domain.check;

public class JobNotFoundException extends RuntimeException {

    public JobNotFoundException(String id) {
        super("Job not found: " + id);
    }
}
