package com.example.songs.adapters.in.web.song.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.songs.adapters.in.web.dto.PagedResponse;
import com.example.songs.adapters.in.web.song.dto.CreateSongRequest;
import com.example.songs.adapters.in.web.song.dto.SongResponse;
import com.example.songs.domain.song.Song;

class SongDtoMapperTest {

    private final SongDtoMapper mapper = new SongDtoMapperImpl();

    @Test
    void toResponse_mapsAllFields() {
        Song song = new Song();
        song.setId(1L);
        song.setTitle("Bohemian Rhapsody");
        song.setArtist("Queen");
        song.setAlbum("A Night at the Opera");

        SongResponse response = mapper.toResponse(song);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.title()).isEqualTo("Bohemian Rhapsody");
        assertThat(response.artist()).isEqualTo("Queen");
        assertThat(response.album()).isEqualTo("A Night at the Opera");
    }

    @Test
    void toResponseList_mapsEachSong() {
        Song song = new Song();
        song.setId(2L);
        song.setTitle("Test");
        song.setArtist("Tester");

        List<SongResponse> responses = mapper.toResponseList(List.of(song));

        assertThat(responses).hasSize(1);
        assertThat(responses.getFirst().title()).isEqualTo("Test");
    }

    @Test
    void toDomain_mapsFieldsAndLeavesIdNull() {
        CreateSongRequest request = new CreateSongRequest("Bohemian Rhapsody", "Queen", "A Night at the Opera");

        Song song = mapper.toDomain(request);

        assertThat(song.getId()).isNull();
        assertThat(song.getTitle()).isEqualTo("Bohemian Rhapsody");
        assertThat(song.getArtist()).isEqualTo("Queen");
        assertThat(song.getAlbum()).isEqualTo("A Night at the Opera");
    }

    @Test
    void toPagedResponse_mapsContentAndPageMetadata() {
        Song song = new Song();
        song.setId(1L);
        song.setTitle("Bohemian Rhapsody");
        song.setArtist("Queen");
        song.setAlbum("A Night at the Opera");

        Pageable pageable = PageRequest.of(0, 20);
        Page<Song> page = new PageImpl<>(List.of(song), pageable, 1);

        PagedResponse<SongResponse> response = mapper.toPagedResponse(page);

        assertThat(response.content()).hasSize(1);
        assertThat(response.content().getFirst().title()).isEqualTo("Bohemian Rhapsody");
        assertThat(response.page()).isZero();
        assertThat(response.size()).isEqualTo(20);
        assertThat(response.totalElements()).isEqualTo(1);
        assertThat(response.totalPages()).isEqualTo(1);
    }
}
