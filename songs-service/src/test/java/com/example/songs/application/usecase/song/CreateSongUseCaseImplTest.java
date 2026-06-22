package com.example.songs.application.usecase.song;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.songs.application.portout.song.SongRepositoryPort;
import com.example.songs.domain.song.Song;

@ExtendWith(MockitoExtension.class)
class CreateSongUseCaseImplTest {

    @Mock
    private SongRepositoryPort songRepositoryPort;

    @Test
    void execute_delegatesToRepositoryAndReturnsSavedSong() {
        CreateSongUseCaseImpl useCase = new CreateSongUseCaseImpl(songRepositoryPort);

        Song toSave = new Song();
        toSave.setTitle("Bohemian Rhapsody");
        toSave.setArtist("Queen");
        toSave.setAlbum("A Night at the Opera");

        Song saved = new Song();
        saved.setId(1L);
        saved.setTitle("Bohemian Rhapsody");
        saved.setArtist("Queen");
        saved.setAlbum("A Night at the Opera");

        when(songRepositoryPort.save(toSave)).thenReturn(saved);

        Song result = useCase.execute(toSave);

        assertThat(result).isSameAs(saved);
        verify(songRepositoryPort).save(toSave);
    }
}
