package com.example.songs.adapters.out.persistence.song.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.songs.domain.song.Song;
import com.example.songs.infra.persistence.song.SongEntity;

@Mapper(componentModel = "spring")
public interface SongEntityMapper {

    Song toDomain(SongEntity entity);

    List<Song> toDomainList(List<SongEntity> entities);

    SongEntity toEntity(Song song);
}
