package com.example.songs.adapters.out.persistence.song.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.songs.domain.song.Song;
import com.example.songs.infra.persistence.song.SongEntity;

class SongEntityMapperTest {

    private final SongEntityMapper mapper = new SongEntityMapperImpl();

    @Test
    void toDomain_mapsAllFields() {
        SongEntity entity = new SongEntity();
        entity.setId(1L);
        entity.setTitle("Bohemian Rhapsody");
        entity.setArtist("Queen");
        entity.setAlbum("A Night at the Opera");

        Song song = mapper.toDomain(entity);

        assertThat(song.getId()).isEqualTo(1L);
        assertThat(song.getTitle()).isEqualTo("Bohemian Rhapsody");
        assertThat(song.getArtist()).isEqualTo("Queen");
        assertThat(song.getAlbum()).isEqualTo("A Night at the Opera");
    }

    @Test
    void toDomain_nullEntityReturnsNull() {
        assertThat(mapper.toDomain(null)).isNull();
    }

    @Test
    void toDomainList_mapsEachEntity() {
        SongEntity entity = new SongEntity();
        entity.setId(2L);
        entity.setTitle("Test");
        entity.setArtist("Tester");

        List<Song> songs = mapper.toDomainList(List.of(entity));

        assertThat(songs).hasSize(1);
        assertThat(songs.getFirst().getTitle()).isEqualTo("Test");
        assertThat(songs.getFirst().getArtist()).isEqualTo("Tester");
    }

    @Test
    void toEntity_mapsAllFields() {
        Song song = new Song();
        song.setId(5L);
        song.setTitle("Bohemian Rhapsody");
        song.setArtist("Queen");
        song.setAlbum("A Night at the Opera");

        SongEntity entity = mapper.toEntity(song);

        assertThat(entity.getId()).isEqualTo(5L);
        assertThat(entity.getTitle()).isEqualTo("Bohemian Rhapsody");
        assertThat(entity.getArtist()).isEqualTo("Queen");
        assertThat(entity.getAlbum()).isEqualTo("A Night at the Opera");
    }
}
