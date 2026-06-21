package com.example.sqlite.application.portin.user;

import java.util.List;

import com.example.sqlite.domain.user.User;

public interface ListUsersUseCase {

    List<User> execute();
}
