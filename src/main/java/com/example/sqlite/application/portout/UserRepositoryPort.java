package com.example.sqlite.application.portout;

import java.util.List;

import com.example.sqlite.domain.User;

public interface UserRepositoryPort {

    List<User> findAll();

    User save(User user);
}
