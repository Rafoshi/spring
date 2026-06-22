package com.example.songs.application.usecase.song;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.songs.application.portin.song.ListSongsUseCase;
import com.example.songs.application.portout.song.SongRepositoryPort;
import com.example.songs.domain.song.Song;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListSongsUseCaseImpl implements ListSongsUseCase {

    private final SongRepositoryPort songRepositoryPort;

    @Override
    public Page<Song> execute(Pageable pageable) {
        return songRepositoryPort.findAll(pageable);
    }
}
