package com.example.songs.application.usecase.song;

import org.springframework.stereotype.Service;

import com.example.songs.application.portin.song.CreateSongUseCase;
import com.example.songs.application.portout.song.SongRepositoryPort;
import com.example.songs.domain.song.Song;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateSongUseCaseImpl implements CreateSongUseCase {

    private final SongRepositoryPort songRepositoryPort;

    @Override
    public Song execute(Song song) {
        return songRepositoryPort.save(song);
    }
}
