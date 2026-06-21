package com.example.sqlite.application.portout.user;

import java.util.List;
import java.util.Optional;

import com.example.sqlite.domain.user.User;

public interface UserRepositoryPort {

    List<User> findAll();

    Optional<User> findById(Long id);

    User save(User user);

    void deleteById(Long id);
}
