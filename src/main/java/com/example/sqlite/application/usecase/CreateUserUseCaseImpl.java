package com.example.sqlite.application.usecase;

import org.springframework.stereotype.Service;

import com.example.sqlite.application.portin.CreateUserUseCase;
import com.example.sqlite.application.portout.UserRepositoryPort;
import com.example.sqlite.domain.User;

@Service
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public CreateUserUseCaseImpl(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public User execute(User user) {
        return userRepositoryPort.save(user);
    }
}
