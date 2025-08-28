package com.litwnb.reelindex.controller;

import com.litwnb.reelindex.model.MovieDTO;
import com.litwnb.reelindex.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
@AutoConfigureMockMvc(addFilters = false)
public class MovieControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private MovieService movieService;

    @Test
    void getMovieById_returnsMovieDto() throws Exception {
        UUID id = UUID.randomUUID();
        MovieDTO dto = MovieDTO.builder().title("Inception").releaseYear(2010).build();

        when(movieService.getMovieById(id)).thenReturn(dto);

        mockMvc.perform(get("/api/movies/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Inception"))
                .andExpect(jsonPath("$.releaseYear").value(2010));
    }

    @Test
    void listMovies_returnsPagedMovies() throws Exception {
        MovieDTO m1 = MovieDTO.builder().id(UUID.randomUUID()).title("Movie1").releaseYear(2000).build();
        MovieDTO m2 = MovieDTO.builder().id(UUID.randomUUID()).title("Movie2").releaseYear(2025).build();

        Page<MovieDTO> page = new PageImpl<>(List.of(m1, m2));
        when(movieService.listMovies(any(), any(), any(), any(), any())).thenReturn(page);

        mockMvc.perform(get("/api/movies")
                        .param("title", "Mov"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Movie1"))
                .andExpect(jsonPath("$.content[0].releaseYear").value(2000))
                .andExpect(jsonPath("$.content[1].title").value("Movie2"))
                .andExpect(jsonPath("$.content[1].releaseYear").value(2025));
    }
}
