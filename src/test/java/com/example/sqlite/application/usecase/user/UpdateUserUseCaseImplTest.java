package com.example.sqlite.application.usecase.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.sqlite.application.portout.user.UserRepositoryPort;
import com.example.sqlite.domain.user.User;
import com.example.sqlite.domain.user.UserNotFoundException;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseImplTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Test
    void execute_existingUser_updatesNameAndEmailAndSaves() {
        UpdateUserUseCaseImpl useCase = new UpdateUserUseCaseImpl(userRepositoryPort);

        User existing = new User();
        existing.setId(1L);
        existing.setName("Old");
        existing.setEmail("old@example.com");

        User changes = new User();
        changes.setName("New");
        changes.setEmail("new@example.com");

        User saved = new User();
        saved.setId(1L);
        saved.setName("New");
        saved.setEmail("new@example.com");

        when(userRepositoryPort.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepositoryPort.save(ArgumentMatchers.any(User.class))).thenReturn(saved);

        User result = useCase.execute(1L, changes);

        assertThat(result).isSameAs(saved);
        verify(userRepositoryPort).save(existing);
        assertThat(existing.getName()).isEqualTo("New");
        assertThat(existing.getEmail()).isEqualTo("new@example.com");
    }

    @Test
    void execute_userNotFound_throwsUserNotFoundException() {
        UpdateUserUseCaseImpl useCase = new UpdateUserUseCaseImpl(userRepositoryPort);

        when(userRepositoryPort.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(99L, new User()))
                .isInstanceOf(UserNotFoundException.class);
    }
}
