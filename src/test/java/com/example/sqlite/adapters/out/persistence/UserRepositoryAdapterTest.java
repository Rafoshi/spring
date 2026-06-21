package com.example.sqlite.adapters.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.sqlite.adapters.out.persistence.mapper.UserEntityMapper;
import com.example.sqlite.domain.User;
import com.example.sqlite.infra.persistence.UserEntity;
import com.example.sqlite.infra.persistence.UserJpaRepository;

@ExtendWith(MockitoExtension.class)
class UserRepositoryAdapterTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private UserEntityMapper userEntityMapper;

    @Test
    void findAll_delegatesToJpaRepositoryAndMapsToDomain() {
        UserRepositoryAdapter adapter = new UserRepositoryAdapter(userJpaRepository, userEntityMapper);

        List<UserEntity> entities = List.of(new UserEntity());
        List<User> users = List.of(new User());

        when(userJpaRepository.findAll()).thenReturn(entities);
        when(userEntityMapper.toDomainList(entities)).thenReturn(users);

        assertThat(adapter.findAll()).isSameAs(users);
    }

    @Test
    void save_mapsToEntityPersistsAndMapsBackToDomain() {
        UserRepositoryAdapter adapter = new UserRepositoryAdapter(userJpaRepository, userEntityMapper);

        User user = new User();
        UserEntity entity = new UserEntity();
        UserEntity savedEntity = new UserEntity();
        User savedUser = new User();

        when(userEntityMapper.toEntity(user)).thenReturn(entity);
        when(userJpaRepository.save(entity)).thenReturn(savedEntity);
        when(userEntityMapper.toDomain(savedEntity)).thenReturn(savedUser);

        assertThat(adapter.save(user)).isSameAs(savedUser);
    }
}
