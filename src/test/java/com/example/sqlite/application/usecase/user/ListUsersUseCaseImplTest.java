package com.example.sqlite.application.usecase.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.sqlite.application.portout.user.UserRepositoryPort;
import com.example.sqlite.domain.user.User;

@ExtendWith(MockitoExtension.class)
class ListUsersUseCaseImplTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Test
    void execute_delegatesToRepositoryFindAll() {
        ListUsersUseCaseImpl useCase = new ListUsersUseCaseImpl(userRepositoryPort);

        User user = new User();
        user.setId(1L);
        user.setName("Ioshi");
        user.setEmail("rafa.ioshi@gmail.com");
        List<User> users = List.of(user);

        when(userRepositoryPort.findAll()).thenReturn(users);

        assertThat(useCase.execute()).isSameAs(users);
    }
}
