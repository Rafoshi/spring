package com.example.songs.domain.song;

public class SongNotFoundException extends RuntimeException {

    public SongNotFoundException(Long id) {
        super("Song not found: " + id);
    }
}
