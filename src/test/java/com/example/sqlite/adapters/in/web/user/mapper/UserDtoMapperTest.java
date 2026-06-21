package com.example.sqlite.adapters.in.web.user.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.sqlite.adapters.in.web.user.dto.CreateUserRequest;
import com.example.sqlite.adapters.in.web.user.dto.UserResponse;
import com.example.sqlite.domain.user.User;

class UserDtoMapperTest {

    private final UserDtoMapper mapper = new UserDtoMapperImpl();

    @Test
    void toResponse_mapsAllFields() {
        User user = new User();
        user.setId(1L);
        user.setName("Ioshi");
        user.setEmail("rafa.ioshi@gmail.com");

        UserResponse response = mapper.toResponse(user);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("Ioshi");
        assertThat(response.email()).isEqualTo("rafa.ioshi@gmail.com");
    }

    @Test
    void toResponseList_mapsEachUser() {
        User user = new User();
        user.setId(2L);
        user.setName("Test");
        user.setEmail("test@example.com");

        List<UserResponse> responses = mapper.toResponseList(List.of(user));

        assertThat(responses).hasSize(1);
        assertThat(responses.getFirst().name()).isEqualTo("Test");
    }

    @Test
    void toDomain_mapsNameAndEmailAndLeavesIdNull() {
        CreateUserRequest request = new CreateUserRequest("Ioshi", "rafa.ioshi@gmail.com");

        User user = mapper.toDomain(request);

        assertThat(user.getId()).isNull();
        assertThat(user.getName()).isEqualTo("Ioshi");
        assertThat(user.getEmail()).isEqualTo("rafa.ioshi@gmail.com");
    }
}
