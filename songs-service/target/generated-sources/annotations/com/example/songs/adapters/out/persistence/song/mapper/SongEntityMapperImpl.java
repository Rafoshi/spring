package com.example.songs.adapters.out.persistence.song.mapper;

import com.example.songs.domain.song.Song;
import com.example.songs.infra.persistence.song.SongEntity;
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
public class SongEntityMapperImpl implements SongEntityMapper {

    @Override
    public Song toDomain(SongEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Song song = new Song();

        song.setId( entity.getId() );
        song.setTitle( entity.getTitle() );
        song.setArtist( entity.getArtist() );
        song.setAlbum( entity.getAlbum() );

        return song;
    }

    @Override
    public List<Song> toDomainList(List<SongEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<Song> list = new ArrayList<Song>( entities.size() );
        for ( SongEntity songEntity : entities ) {
            list.add( toDomain( songEntity ) );
        }

        return list;
    }

    @Override
    public SongEntity toEntity(Song song) {
        if ( song == null ) {
            return null;
        }

        SongEntity songEntity = new SongEntity();

        songEntity.setId( song.getId() );
        songEntity.setTitle( song.getTitle() );
        songEntity.setArtist( song.getArtist() );
        songEntity.setAlbum( song.getAlbum() );

        return songEntity;
    }
}
