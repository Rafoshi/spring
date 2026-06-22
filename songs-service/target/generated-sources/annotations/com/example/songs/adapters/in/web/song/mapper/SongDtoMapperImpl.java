package com.example.songs.adapters.in.web.song.mapper;

import com.example.songs.adapters.in.web.song.dto.CreateSongRequest;
import com.example.songs.adapters.in.web.song.dto.SongResponse;
import com.example.songs.adapters.in.web.song.dto.UpdateSongRequest;
import com.example.songs.domain.song.Song;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-22T14:16:00-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 26.0.1 (Oracle Corporation)"
)
@Component
public class SongDtoMapperImpl implements SongDtoMapper {

    @Override
    public SongResponse toResponse(Song song) {
        if ( song == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        String artist = null;
        String album = null;

        id = song.getId();
        title = song.getTitle();
        artist = song.getArtist();
        album = song.getAlbum();

        SongResponse songResponse = new SongResponse( id, title, artist, album );

        return songResponse;
    }

    @Override
    public List<SongResponse> toResponseList(List<Song> songs) {
        if ( songs == null ) {
            return null;
        }

        List<SongResponse> list = new ArrayList<SongResponse>( songs.size() );
        for ( Song song : songs ) {
            list.add( toResponse( song ) );
        }

        return list;
    }

    @Override
    public Song toDomain(CreateSongRequest request) {
        if ( request == null ) {
            return null;
        }

        Song song = new Song();

        song.setTitle( request.title() );
        song.setArtist( request.artist() );
        song.setAlbum( request.album() );

        return song;
    }

    @Override
    public Song toDomain(UpdateSongRequest request) {
        if ( request == null ) {
            return null;
        }

        Song song = new Song();

        song.setTitle( request.title() );
        song.setArtist( request.artist() );
        song.setAlbum( request.album() );

        return song;
    }
}
