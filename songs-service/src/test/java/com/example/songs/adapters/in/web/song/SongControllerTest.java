package com.example.songs.adapters.in.web.song;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.songs.adapters.in.web.dto.PagedResponse;
import com.example.songs.adapters.in.web.song.dto.CreateSongRequest;
import com.example.songs.adapters.in.web.song.dto.SongResponse;
import com.example.songs.adapters.in.web.song.dto.UpdateSongRequest;
import com.example.songs.adapters.in.web.song.mapper.SongDtoMapper;
import com.example.songs.application.portin.song.CreateSongUseCase;
import com.example.songs.application.portin.song.DeleteSongUseCase;
import com.example.songs.application.portin.song.ListSongsUseCase;
import com.example.songs.application.portin.song.UpdateSongUseCase;
import com.example.songs.domain.song.Song;
import com.example.songs.domain.song.SongNotFoundException;

@WebMvcTest(SongController.class)
class SongControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ListSongsUseCase listSongsUseCase;

    @MockitoBean
    private CreateSongUseCase createSongUseCase;

    @MockitoBean
    private UpdateSongUseCase updateSongUseCase;

    @MockitoBean
    private DeleteSongUseCase deleteSongUseCase;

    @MockitoBean
    private SongDtoMapper songDtoMapper;

    @Test
    void getAllSongs_returnsPagedResponse() throws Exception {
        Song song = new Song();
        song.setId(1L);
        song.setTitle("Bohemian Rhapsody");
        song.setArtist("Queen");
        song.setAlbum("A Night at the Opera");
        SongResponse response = new SongResponse(1L, "Bohemian Rhapsody", "Queen", "A Night at the Opera");

        Pageable pageable = PageRequest.of(0, 20);
        Page<Song> page = new PageImpl<>(List.of(song), pageable, 1);
        PagedResponse<SongResponse> pagedResponse = new PagedResponse<>(List.of(response), 0, 20, 1, 1);

        when(listSongsUseCase.execute(any(Pageable.class))).thenReturn(page);
        when(songDtoMapper.toPagedResponse(page)).thenReturn(pagedResponse);

        mockMvc.perform(get("/songs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Bohemian Rhapsody"))
                .andExpect(jsonPath("$.content[0].artist").value("Queen"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void createSong_validPayload_returns201() throws Exception {
        Song domainSong = new Song();
        domainSong.setTitle("Bohemian Rhapsody");
        domainSong.setArtist("Queen");
        domainSong.setAlbum("A Night at the Opera");

        Song saved = new Song();
        saved.setId(1L);
        saved.setTitle("Bohemian Rhapsody");
        saved.setArtist("Queen");
        saved.setAlbum("A Night at the Opera");

        SongResponse response = new SongResponse(1L, "Bohemian Rhapsody", "Queen", "A Night at the Opera");

        when(songDtoMapper.toDomain(any(CreateSongRequest.class))).thenReturn(domainSong);
        when(createSongUseCase.execute(domainSong)).thenReturn(saved);
        when(songDtoMapper.toResponse(saved)).thenReturn(response);

        mockMvc.perform(post("/songs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Bohemian Rhapsody\",\"artist\":\"Queen\",\"album\":\"A Night at the Opera\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.artist").value("Queen"));
    }

    @Test
    void createSong_blankTitle_returns400AndSkipsUseCase() throws Exception {
        mockMvc.perform(post("/songs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"\",\"artist\":\"Queen\",\"album\":\"A Night at the Opera\"}"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(createSongUseCase);
    }

    @Test
    void updateSong_validPayload_returns200() throws Exception {
        Song domainSong = new Song();
        domainSong.setTitle("New Title");
        domainSong.setArtist("New Artist");
        domainSong.setAlbum("New Album");

        Song updated = new Song();
        updated.setId(1L);
        updated.setTitle("New Title");
        updated.setArtist("New Artist");
        updated.setAlbum("New Album");

        SongResponse response = new SongResponse(1L, "New Title", "New Artist", "New Album");

        when(songDtoMapper.toDomain(any(UpdateSongRequest.class))).thenReturn(domainSong);
        when(updateSongUseCase.execute(eq(1L), eq(domainSong))).thenReturn(updated);
        when(songDtoMapper.toResponse(updated)).thenReturn(response);

        mockMvc.perform(put("/songs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"New Title\",\"artist\":\"New Artist\",\"album\":\"New Album\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Title"))
                .andExpect(jsonPath("$.artist").value("New Artist"));
    }

    @Test
    void updateSong_songNotFound_returns404() throws Exception {
        Song domainSong = new Song();
        domainSong.setTitle("Title");
        domainSong.setArtist("Artist");

        when(songDtoMapper.toDomain(any(UpdateSongRequest.class))).thenReturn(domainSong);
        when(updateSongUseCase.execute(eq(99L), eq(domainSong))).thenThrow(new SongNotFoundException(99L));

        mockMvc.perform(put("/songs/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Title\",\"artist\":\"Artist\",\"album\":\"Album\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteSong_existingSong_returns204() throws Exception {
        mockMvc.perform(delete("/songs/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteSong_songNotFound_returns404() throws Exception {
        org.mockito.Mockito.doThrow(new SongNotFoundException(99L)).when(deleteSongUseCase).execute(99L);

        mockMvc.perform(delete("/songs/99"))
                .andExpect(status().isNotFound());
    }
}
