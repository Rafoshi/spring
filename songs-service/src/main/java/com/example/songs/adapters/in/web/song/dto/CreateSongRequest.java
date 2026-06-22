package com.example.songs.adapters.in.web.song.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Payload to create a song")
public record CreateSongRequest(
        @NotBlank @Schema(description = "Song title", example = "Bohemian Rhapsody") String title,
        @NotBlank @Schema(description = "Song artist", example = "Queen") String artist,
        @Schema(description = "Song album", example = "A Night at the Opera") String album) {
}
