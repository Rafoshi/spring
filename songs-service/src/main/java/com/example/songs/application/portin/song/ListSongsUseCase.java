package com.example.songs.application.portin.song;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.songs.domain.song.Song;

public interface ListSongsUseCase {

    Page<Song> execute(Pageable pageable);
}
