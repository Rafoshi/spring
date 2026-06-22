package com.example.sqlite.application.usecase.user;

import org.springframework.stereotype.Service;

import com.example.sqlite.application.portin.user.UpdateUserUseCase;
import com.example.sqlite.application.portout.user.UserRepositoryPort;
import com.example.sqlite.domain.user.User;
import com.example.sqlite.domain.user.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public User execute(Long id, User user) {
        User existing = userRepositoryPort.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        existing.setName(user.getName());
        existing.setEmail(user.getEmail());

        return userRepositoryPort.save(existing);
    }
}
