package com.example.songs.adapters.out.persistence.song;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.songs.adapters.out.persistence.song.mapper.SongEntityMapper;
import com.example.songs.domain.song.Song;
import com.example.songs.infra.persistence.song.SongEntity;
import com.example.songs.infra.persistence.song.SongJpaRepository;

@ExtendWith(MockitoExtension.class)
class SongRepositoryAdapterTest {

    @Mock
    private SongJpaRepository songJpaRepository;

    @Mock
    private SongEntityMapper songEntityMapper;

    @Test
    void findAll_delegatesToJpaRepositoryAndMapsToDomain() {
        SongRepositoryAdapter adapter = new SongRepositoryAdapter(songJpaRepository, songEntityMapper);

        Pageable pageable = PageRequest.of(0, 20);
        SongEntity entity = new SongEntity();
        Song song = new Song();
        Page<SongEntity> entityPage = new PageImpl<>(List.of(entity), pageable, 1);

        when(songJpaRepository.findAll(pageable)).thenReturn(entityPage);
        when(songEntityMapper.toDomain(entity)).thenReturn(song);

        Page<Song> result = adapter.findAll(pageable);

        assertThat(result.getContent()).containsExactly(song);
        assertThat(result.getTotalElements()).isEqualTo(1);
    }

    @Test
    void save_mapsToEntityPersistsAndMapsBackToDomain() {
        SongRepositoryAdapter adapter = new SongRepositoryAdapter(songJpaRepository, songEntityMapper);

        Song song = new Song();
        SongEntity entity = new SongEntity();
        SongEntity savedEntity = new SongEntity();
        Song savedSong = new Song();

        when(songEntityMapper.toEntity(song)).thenReturn(entity);
        when(songJpaRepository.save(entity)).thenReturn(savedEntity);
        when(songEntityMapper.toDomain(savedEntity)).thenReturn(savedSong);

        assertThat(adapter.save(song)).isSameAs(savedSong);
    }

    @Test
    void findById_existingId_returnsMappedSong() {
        SongRepositoryAdapter adapter = new SongRepositoryAdapter(songJpaRepository, songEntityMapper);

        SongEntity entity = new SongEntity();
        Song song = new Song();

        when(songJpaRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(songEntityMapper.toDomain(entity)).thenReturn(song);

        assertThat(adapter.findById(1L)).contains(song);
    }

    @Test
    void findById_missingId_returnsEmpty() {
        SongRepositoryAdapter adapter = new SongRepositoryAdapter(songJpaRepository, songEntityMapper);

        when(songJpaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThat(adapter.findById(99L)).isEmpty();
    }

    @Test
    void deleteById_delegatesToJpaRepository() {
        SongRepositoryAdapter adapter = new SongRepositoryAdapter(songJpaRepository, songEntityMapper);

        adapter.deleteById(1L);

        verify(songJpaRepository).deleteById(1L);
    }
}
