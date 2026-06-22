package com.example.songs.application.usecase.song;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.songs.application.portout.song.SongRepositoryPort;
import com.example.songs.domain.song.Song;

@ExtendWith(MockitoExtension.class)
class ListSongsUseCaseImplTest {

    @Mock
    private SongRepositoryPort songRepositoryPort;

    @Test
    void execute_delegatesToRepositoryFindAll() {
        ListSongsUseCaseImpl useCase = new ListSongsUseCaseImpl(songRepositoryPort);

        Song song = new Song();
        song.setId(1L);
        song.setTitle("Bohemian Rhapsody");
        song.setArtist("Queen");
        song.setAlbum("A Night at the Opera");
        Pageable pageable = PageRequest.of(0, 20);
        Page<Song> page = new PageImpl<>(List.of(song), pageable, 1);

        when(songRepositoryPort.findAll(pageable)).thenReturn(page);

        assertThat(useCase.execute(pageable)).isSameAs(page);
    }
}
