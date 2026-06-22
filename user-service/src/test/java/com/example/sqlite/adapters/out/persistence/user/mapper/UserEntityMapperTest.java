package com.example.sqlite.adapters.out.persistence.user.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.sqlite.domain.user.User;
import com.example.sqlite.infra.persistence.user.UserEntity;

class UserEntityMapperTest {

    private final UserEntityMapper mapper = new UserEntityMapperImpl();

    @Test
    void toDomain_mapsAllFields() {
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setName("Ioshi");
        entity.setEmail("rafa.ioshi@gmail.com");

        User user = mapper.toDomain(entity);

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("Ioshi");
        assertThat(user.getEmail()).isEqualTo("rafa.ioshi@gmail.com");
    }

    @Test
    void toDomain_nullEntityReturnsNull() {
        assertThat(mapper.toDomain(null)).isNull();
    }

    @Test
    void toDomainList_mapsEachEntity() {
        UserEntity entity = new UserEntity();
        entity.setId(2L);
        entity.setName("Test");
        entity.setEmail("test@example.com");

        List<User> users = mapper.toDomainList(List.of(entity));

        assertThat(users).hasSize(1);
        assertThat(users.getFirst().getName()).isEqualTo("Test");
        assertThat(users.getFirst().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void toEntity_mapsAllFields() {
        User user = new User();
        user.setId(5L);
        user.setName("Ioshi");
        user.setEmail("rafa.ioshi@gmail.com");

        UserEntity entity = mapper.toEntity(user);

        assertThat(entity.getId()).isEqualTo(5L);
        assertThat(entity.getName()).isEqualTo("Ioshi");
        assertThat(entity.getEmail()).isEqualTo("rafa.ioshi@gmail.com");
    }
}
