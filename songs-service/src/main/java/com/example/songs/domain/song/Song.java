package com.example.songs.domain.song;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Song {

    private Long id;

    private String title;

    private String artist;

    private String album;
}
