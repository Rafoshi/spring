package com.example.sqlite.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.sqlite.application.portout.UserRepositoryPort;
import com.example.sqlite.domain.User;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseImplTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Test
    void execute_delegatesToRepositoryAndReturnsSavedUser() {
        CreateUserUseCaseImpl useCase = new CreateUserUseCaseImpl(userRepositoryPort);

        User toSave = new User();
        toSave.setName("Ioshi");
        toSave.setEmail("rafa.ioshi@gmail.com");

        User saved = new User();
        saved.setId(1L);
        saved.setName("Ioshi");
        saved.setEmail("rafa.ioshi@gmail.com");

        when(userRepositoryPort.save(toSave)).thenReturn(saved);

        User result = useCase.execute(toSave);

        assertThat(result).isSameAs(saved);
        verify(userRepositoryPort).save(toSave);
    }
}
