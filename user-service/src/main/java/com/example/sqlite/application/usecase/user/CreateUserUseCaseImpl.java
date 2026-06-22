package com.example.sqlite.application.usecase.user;

import org.springframework.stereotype.Service;

import com.example.sqlite.application.portin.user.CreateUserUseCase;
import com.example.sqlite.application.portout.user.UserRepositoryPort;
import com.example.sqlite.domain.user.User;

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
