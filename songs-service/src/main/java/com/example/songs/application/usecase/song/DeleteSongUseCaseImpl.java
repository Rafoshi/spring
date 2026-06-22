package com.example.songs.application.usecase.song;

import org.springframework.stereotype.Service;

import com.example.songs.application.portin.song.DeleteSongUseCase;
import com.example.songs.application.portout.song.SongRepositoryPort;
import com.example.songs.domain.song.SongNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteSongUseCaseImpl implements DeleteSongUseCase {

    private final SongRepositoryPort songRepositoryPort;

    @Override
    public void execute(Long id) {
        songRepositoryPort.findById(id)
                .orElseThrow(() -> new SongNotFoundException(id));

        songRepositoryPort.deleteById(id);
    }
}
