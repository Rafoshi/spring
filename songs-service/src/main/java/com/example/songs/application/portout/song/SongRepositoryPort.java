package com.example.songs.application.portout.song;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.songs.domain.song.Song;

public interface SongRepositoryPort {

    Page<Song> findAll(Pageable pageable);

    Optional<Song> findById(Long id);

    Song save(Song song);

    void deleteById(Long id);
}
