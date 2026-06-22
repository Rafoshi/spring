package com.example.songs.adapters.in.web.song;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.songs.adapters.in.web.dto.PagedResponse;
import com.example.songs.adapters.in.web.song.dto.CreateSongRequest;
import com.example.songs.adapters.in.web.song.dto.UpdateSongRequest;
import com.example.songs.adapters.in.web.song.dto.SongResponse;
import com.example.songs.adapters.in.web.song.mapper.SongDtoMapper;
import com.example.songs.application.portin.song.CreateSongUseCase;
import com.example.songs.application.portin.song.DeleteSongUseCase;
import com.example.songs.application.portin.song.ListSongsUseCase;
import com.example.songs.application.portin.song.UpdateSongUseCase;
import com.example.songs.domain.song.Song;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Songs", description = "Song management endpoints")
@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {

    private final ListSongsUseCase listSongsUseCase;
    private final CreateSongUseCase createSongUseCase;
    private final UpdateSongUseCase updateSongUseCase;
    private final DeleteSongUseCase deleteSongUseCase;
    private final SongDtoMapper songDtoMapper;

    @Operation(summary = "List all songs, paged")
    @ApiResponse(responseCode = "200", description = "Songs returned successfully")
    @GetMapping
    public PagedResponse<SongResponse> getAllSongs(@ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        return songDtoMapper.toPagedResponse(listSongsUseCase.execute(pageable));
    }

    @Operation(summary = "Create a song")
    @ApiResponse(responseCode = "201", description = "Song created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid payload",
            content = @Content(schema = @Schema(type = "string")))
    @PostMapping
    public ResponseEntity<SongResponse> createSong(@Valid @RequestBody CreateSongRequest request) {
        Song created = createSongUseCase.execute(songDtoMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(songDtoMapper.toResponse(created));
    }

    @Operation(summary = "Update a song")
    @ApiResponse(responseCode = "200", description = "Song updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid payload",
            content = @Content(schema = @Schema(type = "string")))
    @ApiResponse(responseCode = "404", description = "Song not found",
            content = @Content(schema = @Schema(type = "string")))
    @PutMapping("/{id}")
    public ResponseEntity<SongResponse> updateSong(@PathVariable Long id, @Valid @RequestBody UpdateSongRequest request) {
        Song updated = updateSongUseCase.execute(id, songDtoMapper.toDomain(request));
        return ResponseEntity.ok(songDtoMapper.toResponse(updated));
    }

    @Operation(summary = "Delete a song")
    @ApiResponse(responseCode = "204", description = "Song deleted successfully")
    @ApiResponse(responseCode = "404", description = "Song not found",
            content = @Content(schema = @Schema(type = "string")))
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        deleteSongUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
