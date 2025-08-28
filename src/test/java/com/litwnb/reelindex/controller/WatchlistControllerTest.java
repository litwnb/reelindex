package com.litwnb.reelindex.controller;

import com.litwnb.reelindex.model.MovieDTO;
import com.litwnb.reelindex.model.Genre;
import com.litwnb.reelindex.service.WatchlistService;
import com.litwnb.reelindex.util.TestSecurityUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WatchlistController.class)
class WatchlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WatchlistService watchlistService;

    @Test
    void getWatchlist_returnsMovies() throws Exception {
        MovieDTO movie = MovieDTO.builder()
                .id(UUID.randomUUID())
                .title("Inception")
                .releaseYear(2010)
                .runtime("2h28m")
                .genre(Genre.SCI_FI)
                .director("Christopher Nolan")
                .build();

        when(watchlistService.getWatchlist(any(UUID.class)))
                .thenReturn(Set.of(movie));

        mockMvc.perform(get("/api/user/watchlist")
                        .with(TestSecurityUtil.testUser()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Inception"));
    }

    @Test
    void addMovie_returnsOk() throws Exception {
        doNothing().when(watchlistService).addMovieToWatchlist(any(UUID.class), any(UUID.class));

        mockMvc.perform(post("/api/user/watchlist")
                        .param("movieId", UUID.randomUUID().toString())
                        .with(TestSecurityUtil.testUser()).with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Movie added"));
    }

    @Test
    void deleteMovie_returnsOk() throws Exception {
        doNothing().when(watchlistService).removeMovieFromWatchlist(any(UUID.class), any(UUID.class));

        mockMvc.perform(delete("/api/user/watchlist/" + UUID.randomUUID())
                        .with(TestSecurityUtil.testUser()).with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Movie removed"));
    }
}
