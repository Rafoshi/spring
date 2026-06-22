package com.example.songs.application.usecase.song;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.songs.application.portout.song.SongRepositoryPort;
import com.example.songs.domain.song.Song;
import com.example.songs.domain.song.SongNotFoundException;

@ExtendWith(MockitoExtension.class)
class DeleteSongUseCaseImplTest {

    @Mock
    private SongRepositoryPort songRepositoryPort;

    @Test
    void execute_existingSong_deletesById() {
        DeleteSongUseCaseImpl useCase = new DeleteSongUseCaseImpl(songRepositoryPort);

        when(songRepositoryPort.findById(1L)).thenReturn(Optional.of(new Song()));

        useCase.execute(1L);

        verify(songRepositoryPort).deleteById(1L);
    }

    @Test
    void execute_songNotFound_throwsAndNeverDeletes() {
        DeleteSongUseCaseImpl useCase = new DeleteSongUseCaseImpl(songRepositoryPort);

        when(songRepositoryPort.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(99L))
                .isInstanceOf(SongNotFoundException.class);

        verify(songRepositoryPort, never()).deleteById(99L);
    }
}
