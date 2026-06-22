package com.example.songs.application.usecase.song;

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

import com.example.songs.application.portout.song.SongRepositoryPort;
import com.example.songs.domain.song.Song;
import com.example.songs.domain.song.SongNotFoundException;

@ExtendWith(MockitoExtension.class)
class UpdateSongUseCaseImplTest {

    @Mock
    private SongRepositoryPort songRepositoryPort;

    @Test
    void execute_existingSong_updatesFieldsAndSaves() {
        UpdateSongUseCaseImpl useCase = new UpdateSongUseCaseImpl(songRepositoryPort);

        Song existing = new Song();
        existing.setId(1L);
        existing.setTitle("Old");
        existing.setArtist("Old Artist");
        existing.setAlbum("Old Album");

        Song changes = new Song();
        changes.setTitle("New");
        changes.setArtist("New Artist");
        changes.setAlbum("New Album");

        Song saved = new Song();
        saved.setId(1L);
        saved.setTitle("New");
        saved.setArtist("New Artist");
        saved.setAlbum("New Album");

        when(songRepositoryPort.findById(1L)).thenReturn(Optional.of(existing));
        when(songRepositoryPort.save(ArgumentMatchers.any(Song.class))).thenReturn(saved);

        Song result = useCase.execute(1L, changes);

        assertThat(result).isSameAs(saved);
        verify(songRepositoryPort).save(existing);
        assertThat(existing.getTitle()).isEqualTo("New");
        assertThat(existing.getArtist()).isEqualTo("New Artist");
        assertThat(existing.getAlbum()).isEqualTo("New Album");
    }

    @Test
    void execute_songNotFound_throwsSongNotFoundException() {
        UpdateSongUseCaseImpl useCase = new UpdateSongUseCaseImpl(songRepositoryPort);

        when(songRepositoryPort.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(99L, new Song()))
                .isInstanceOf(SongNotFoundException.class);
    }
}
