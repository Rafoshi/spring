package com.example.songs.application.usecase.song;

import org.springframework.stereotype.Service;

import com.example.songs.application.portin.song.UpdateSongUseCase;
import com.example.songs.application.portout.song.SongRepositoryPort;
import com.example.songs.domain.song.Song;
import com.example.songs.domain.song.SongNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateSongUseCaseImpl implements UpdateSongUseCase {

    private final SongRepositoryPort songRepositoryPort;

    @Override
    public Song execute(Long id, Song song) {
        Song existing = songRepositoryPort.findById(id)
                .orElseThrow(() -> new SongNotFoundException(id));

        existing.setTitle(song.getTitle());
        existing.setArtist(song.getArtist());
        existing.setAlbum(song.getAlbum());

        return songRepositoryPort.save(existing);
    }
}
