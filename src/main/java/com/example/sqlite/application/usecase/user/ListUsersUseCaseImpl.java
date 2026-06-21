package com.example.sqlite.application.usecase.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.sqlite.application.portin.user.ListUsersUseCase;
import com.example.sqlite.application.portout.user.UserRepositoryPort;
import com.example.sqlite.domain.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListUsersUseCaseImpl implements ListUsersUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public List<User> execute() {
        return userRepositoryPort.findAll();
    }
}
