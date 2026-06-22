package com.example.songs.adapters.out.persistence.song;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.example.songs.adapters.out.persistence.song.mapper.SongEntityMapper;
import com.example.songs.application.portout.song.SongRepositoryPort;
import com.example.songs.domain.song.Song;
import com.example.songs.infra.persistence.song.SongJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SongRepositoryAdapter implements SongRepositoryPort {

    private final SongJpaRepository songJpaRepository;
    private final SongEntityMapper songEntityMapper;

    @Override
    public Page<Song> findAll(Pageable pageable) {
        return songJpaRepository.findAll(pageable).map(songEntityMapper::toDomain);
    }

    @Override
    public Optional<Song> findById(Long id) {
        return songJpaRepository.findById(id).map(songEntityMapper::toDomain);
    }

    @Override
    public Song save(Song song) {
        return songEntityMapper.toDomain(songJpaRepository.save(songEntityMapper.toEntity(song)));
    }

    @Override
    public void deleteById(Long id) {
        songJpaRepository.deleteById(id);
    }
}
