package com.example.sqlite.application.usecase.user;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.sqlite.application.portout.user.UserRepositoryPort;
import com.example.sqlite.domain.user.User;
import com.example.sqlite.domain.user.UserNotFoundException;

@ExtendWith(MockitoExtension.class)
class DeleteUserUseCaseImplTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Test
    void execute_existingUser_deletesById() {
        DeleteUserUseCaseImpl useCase = new DeleteUserUseCaseImpl(userRepositoryPort);

        when(userRepositoryPort.findById(1L)).thenReturn(Optional.of(new User()));

        useCase.execute(1L);

        verify(userRepositoryPort).deleteById(1L);
    }

    @Test
    void execute_userNotFound_throwsAndNeverDeletes() {
        DeleteUserUseCaseImpl useCase = new DeleteUserUseCaseImpl(userRepositoryPort);

        when(userRepositoryPort.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(99L))
                .isInstanceOf(UserNotFoundException.class);

        verify(userRepositoryPort, never()).deleteById(99L);
    }
}
