package com.example.sqlite.application.usecase.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<User> execute(Pageable pageable) {
        return userRepositoryPort.findAll(pageable);
    }
}
