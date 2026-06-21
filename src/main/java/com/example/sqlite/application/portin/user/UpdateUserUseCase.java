package com.example.sqlite.application.portin.user;

import com.example.sqlite.domain.user.User;

public interface UpdateUserUseCase {

    User execute(Long id, User user);
}
