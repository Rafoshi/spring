package com.example.sqlite.adapters.out.persistence;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.sqlite.adapters.out.persistence.mapper.UserEntityMapper;
import com.example.sqlite.application.portout.UserRepositoryPort;
import com.example.sqlite.domain.User;
import com.example.sqlite.infra.persistence.UserJpaRepository;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository userJpaRepository;
    private final UserEntityMapper userEntityMapper;

    public UserRepositoryAdapter(UserJpaRepository userJpaRepository, UserEntityMapper userEntityMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userEntityMapper = userEntityMapper;
    }

    @Override
    public List<User> findAll() {
        return userEntityMapper.toDomainList(userJpaRepository.findAll());
    }

    @Override
    public User save(User user) {
        return userEntityMapper.toDomain(userJpaRepository.save(userEntityMapper.toEntity(user)));
    }
}
