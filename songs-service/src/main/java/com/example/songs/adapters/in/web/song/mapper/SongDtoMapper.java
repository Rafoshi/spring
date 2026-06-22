package com.example.songs.adapters.in.web.song.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import com.example.songs.adapters.in.web.dto.PagedResponse;
import com.example.songs.adapters.in.web.song.dto.CreateSongRequest;
import com.example.songs.adapters.in.web.song.dto.SongResponse;
import com.example.songs.adapters.in.web.song.dto.UpdateSongRequest;
import com.example.songs.domain.song.Song;

@Mapper(componentModel = "spring")
public interface SongDtoMapper {

    SongResponse toResponse(Song song);

    List<SongResponse> toResponseList(List<Song> songs);

    Song toDomain(CreateSongRequest request);

    Song toDomain(UpdateSongRequest request);

    default PagedResponse<SongResponse> toPagedResponse(Page<Song> page) {
        return new PagedResponse<>(
                toResponseList(page.getContent()),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }
}
