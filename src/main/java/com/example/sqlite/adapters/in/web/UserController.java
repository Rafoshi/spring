package com.example.sqlite.adapters.in.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sqlite.adapters.in.web.dto.UserResponse;
import com.example.sqlite.adapters.in.web.mapper.UserDtoMapper;
import com.example.sqlite.application.portin.ListUsersUseCase;

@RestController
@RequestMapping("/users")
public class UserController {

    private final ListUsersUseCase listUsersUseCase;
    private final UserDtoMapper userDtoMapper;

    public UserController(ListUsersUseCase listUsersUseCase, UserDtoMapper userDtoMapper) {
        this.listUsersUseCase = listUsersUseCase;
        this.userDtoMapper = userDtoMapper;
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userDtoMapper.toResponseList(listUsersUseCase.execute());
    }
}
