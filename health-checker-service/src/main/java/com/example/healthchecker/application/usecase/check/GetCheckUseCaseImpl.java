package com.example.healthchecker.application.usecase.check;

import com.example.healthchecker.application.portin.check.GetCheckUseCase;
import com.example.healthchecker.application.portout.check.JobRepositoryPort;
import com.example.healthchecker.domain.check.Job;
import com.example.healthchecker.domain.check.JobNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCheckUseCaseImpl implements GetCheckUseCase {

    private final JobRepositoryPort jobRepositoryPort;

    @Override
    public Job execute(String id) {
        return jobRepositoryPort.findById(id)
                .orElseThrow(() -> new JobNotFoundException(id));
    }
}
