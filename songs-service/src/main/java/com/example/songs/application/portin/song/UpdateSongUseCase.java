package com.example.songs.application.portin.song;

import com.example.songs.domain.song.Song;

public interface UpdateSongUseCase {

    Song execute(Long id, Song song);
}
