package com.example.sqlite.adapters.out.persistence.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.sqlite.adapters.out.persistence.user.mapper.UserEntityMapper;
import com.example.sqlite.domain.user.User;
import com.example.sqlite.infra.persistence.user.UserEntity;
import com.example.sqlite.infra.persistence.user.UserJpaRepository;

@ExtendWith(MockitoExtension.class)
class UserRepositoryAdapterTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private UserEntityMapper userEntityMapper;

    @Test
    void findAll_delegatesToJpaRepositoryAndMapsToDomain() {
        UserRepositoryAdapter adapter = new UserRepositoryAdapter(userJpaRepository, userEntityMapper);

        Pageable pageable = PageRequest.of(0, 20);
        UserEntity entity = new UserEntity();
        User user = new User();
        Page<UserEntity> entityPage = new PageImpl<>(List.of(entity), pageable, 1);

        when(userJpaRepository.findAll(pageable)).thenReturn(entityPage);
        when(userEntityMapper.toDomain(entity)).thenReturn(user);

        Page<User> result = adapter.findAll(pageable);

        assertThat(result.getContent()).containsExactly(user);
        assertThat(result.getTotalElements()).isEqualTo(1);
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

    @Test
    void findById_existingId_returnsMappedUser() {
        UserRepositoryAdapter adapter = new UserRepositoryAdapter(userJpaRepository, userEntityMapper);

        UserEntity entity = new UserEntity();
        User user = new User();

        when(userJpaRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(userEntityMapper.toDomain(entity)).thenReturn(user);

        assertThat(adapter.findById(1L)).contains(user);
    }

    @Test
    void findById_missingId_returnsEmpty() {
        UserRepositoryAdapter adapter = new UserRepositoryAdapter(userJpaRepository, userEntityMapper);

        when(userJpaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThat(adapter.findById(99L)).isEmpty();
    }

    @Test
    void deleteById_delegatesToJpaRepository() {
        UserRepositoryAdapter adapter = new UserRepositoryAdapter(userJpaRepository, userEntityMapper);

        adapter.deleteById(1L);

        verify(userJpaRepository).deleteById(1L);
    }
}
