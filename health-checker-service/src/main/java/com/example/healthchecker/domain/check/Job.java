package com.example.healthchecker.domain.check;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Setter
public class Job {

    private String id;

    private List<String> urls;

    private Instant createdAt;

    private volatile JobStatus status;

    private final List<CheckResult> results = new CopyOnWriteArrayList<>();
}
