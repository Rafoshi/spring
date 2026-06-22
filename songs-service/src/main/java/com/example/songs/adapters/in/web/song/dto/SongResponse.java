package com.example.songs.adapters.in.web.song.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "A song returned by the API")
public record SongResponse(
        @Schema(description = "Song id", example = "1") Long id,
        @Schema(description = "Song title", example = "Bohemian Rhapsody") String title,
        @Schema(description = "Song artist", example = "Queen") String artist,
        @Schema(description = "Song album", example = "A Night at the Opera") String album) {
}
