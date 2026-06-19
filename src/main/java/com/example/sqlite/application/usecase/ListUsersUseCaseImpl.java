package com.example.sqlite.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.sqlite.application.portin.ListUsersUseCase;
import com.example.sqlite.application.portout.UserRepositoryPort;
import com.example.sqlite.domain.User;

@Service
public class ListUsersUseCaseImpl implements ListUsersUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public ListUsersUseCaseImpl(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public List<User> execute() {
        return userRepositoryPort.findAll();
    }
}
