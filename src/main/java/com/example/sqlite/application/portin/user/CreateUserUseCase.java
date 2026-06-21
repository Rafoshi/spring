package com.example.sqlite.application.portin.user;

import com.example.sqlite.domain.user.User;

public interface CreateUserUseCase {

    User execute(User user);
}
