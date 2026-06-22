package com.example.songs.application.portin.song;

import com.example.songs.domain.song.Song;

public interface CreateSongUseCase {

    Song execute(Song song);
}
