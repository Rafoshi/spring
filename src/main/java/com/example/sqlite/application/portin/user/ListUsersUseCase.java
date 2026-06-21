package com.example.sqlite.application.portin.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.sqlite.domain.user.User;

public interface ListUsersUseCase {

    Page<User> execute(Pageable pageable);
}
