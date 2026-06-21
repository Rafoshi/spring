package com.example.sqlite.adapters.out.persistence.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.sqlite.adapters.out.persistence.user.mapper.UserEntityMapper;
import com.example.sqlite.application.portout.user.UserRepositoryPort;
import com.example.sqlite.domain.user.User;
import com.example.sqlite.infra.persistence.user.UserJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository userJpaRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public List<User> findAll() {
        return userEntityMapper.toDomainList(userJpaRepository.findAll());
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id).map(userEntityMapper::toDomain);
    }

    @Override
    public User save(User user) {
        return userEntityMapper.toDomain(userJpaRepository.save(userEntityMapper.toEntity(user)));
    }

    @Override
    public void deleteById(Long id) {
        userJpaRepository.deleteById(id);
    }
}
