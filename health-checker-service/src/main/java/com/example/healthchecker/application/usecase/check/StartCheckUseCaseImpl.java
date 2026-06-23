package com.example.healthchecker.application.usecase.check;

import com.example.healthchecker.application.portin.check.StartCheckUseCase;
import com.example.healthchecker.application.portout.check.JobRepositoryPort;
import com.example.healthchecker.domain.check.CheckResult;
import com.example.healthchecker.domain.check.Job;
import com.example.healthchecker.domain.check.JobStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Fase 0: dispara uma única thread que checa as URLs sequencialmente.
 * O fan-out concorrente por URL é introduzido na Fase 1.
 */
@Service
@RequiredArgsConstructor
public class StartCheckUseCaseImpl implements StartCheckUseCase {

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    private final JobRepositoryPort jobRepositoryPort;

    @Override
    public Job execute(List<String> urls) {
        Job job = new Job();
        job.setId(UUID.randomUUID().toString());
        job.setUrls(List.copyOf(urls));
        job.setCreatedAt(Instant.now());
        job.setStatus(JobStatus.PENDING);

        jobRepositoryPort.save(job);

        Thread.ofPlatform()
                .name("check-job-" + job.getId())
                .start(() -> runChecks(job));

        return job;
    }

    private void runChecks(Job job) {
        job.setStatus(JobStatus.RUNNING);

        for (String url : job.getUrls()) {
            job.getResults().add(checkUrl(url));
        }

        job.setStatus(JobStatus.DONE);
    }

    private CheckResult checkUrl(String url) {
        long start = System.currentTimeMillis();
        CheckResult result = new CheckResult();
        result.setUrl(url);

        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                    .timeout(Duration.ofSeconds(5))
                    .GET()
                    .build();

            HttpResponse<Void> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.discarding());

            result.setStatusCode(response.statusCode());
            result.setSuccess(response.statusCode() < 400);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setError(e.getMessage());
        } finally {
            result.setDurationMillis(System.currentTimeMillis() - start);
        }

        return result;
    }
}
