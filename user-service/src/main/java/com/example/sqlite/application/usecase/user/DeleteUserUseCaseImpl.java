package com.example.sqlite.application.usecase.user;

import org.springframework.stereotype.Service;

import com.example.sqlite.application.portin.user.DeleteUserUseCase;
import com.example.sqlite.application.portout.user.UserRepositoryPort;
import com.example.sqlite.domain.user.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public void execute(Long id) {
        userRepositoryPort.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepositoryPort.deleteById(id);
    }
}
