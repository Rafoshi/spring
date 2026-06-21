package com.example.sqlite.application.usecase;

import org.springframework.stereotype.Service;

import com.example.sqlite.application.portin.CreateUserUseCase;
import com.example.sqlite.application.portout.UserRepositoryPort;
import com.example.sqlite.domain.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public User execute(User user) {
        return userRepositoryPort.save(user);
    }
}
