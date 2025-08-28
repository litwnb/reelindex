package com.litwnb.reelindex.controller;

import com.litwnb.reelindex.model.UserMovieRatingDTO;
import com.litwnb.reelindex.service.MovieRatingService;
import com.litwnb.reelindex.util.InvalidRatingException;
import com.litwnb.reelindex.util.MovieNotFoundException;
import com.litwnb.reelindex.util.RatingNotFoundException;
import com.litwnb.reelindex.util.TestSecurityUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieRatingController.class)
public class MovieRatingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovieRatingService movieRatingService;

    @Test
    void rateMovie_success() throws Exception {
        String requestJson = """
                {"movieId":"%s","rating":8}
                """.formatted(UUID.randomUUID());

        mockMvc.perform(post("/api/user/ratings")
                        .with(TestSecurityUtil.testUser()).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Rating submitted"));
    }

    @Test
    void getMovieRating_returnsRating() throws Exception {
        UUID movieId = UUID.randomUUID();
        when(movieRatingService.getUserRating(any(UUID.class), any(UUID.class)))
                .thenReturn(9);

        mockMvc.perform(get("/api/user/ratings/" + movieId)
                        .with(TestSecurityUtil.testUser()).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("9"));
    }

    @Test
    void getUserRatings_returnsList() throws Exception {
        UserMovieRatingDTO dto = UserMovieRatingDTO.builder()
                .movieId(UUID.randomUUID())
                .rating(8)
                .build();
        when(movieRatingService.getAllRatingsByUser(any(UUID.class)))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/user/ratings")
                        .with(TestSecurityUtil.testUser()).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rating").value(8));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, 11, 15})
    void rateMovie_invalidRating_returnsBadRequest(int invalidRating) throws Exception {
        String requestJson = """
            {"movieId":"%s","rating":%d}
            """.formatted(UUID.randomUUID(), invalidRating);

        Mockito.doAnswer(invocation -> {
                    int rating = invocation.getArgument(2);
                    if (rating < 1 || rating > 10) {
                        throw new InvalidRatingException();
                    }
                    return null;
                })
                .when(movieRatingService)
                .rateMovie(any(UUID.class), any(UUID.class), anyInt());

        mockMvc.perform(post("/api/user/ratings")
                        .with(TestSecurityUtil.testUser()).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Rating must be between 1 and 10"));
    }

    @Test
    void rateMovie_movieNotFound_returnsNotFound() throws Exception {
        UUID movieId = UUID.randomUUID();
        String requestJson = """
            {"movieId":"%s","rating":8}
            """.formatted(movieId);

        doThrow(new MovieNotFoundException())
                .when(movieRatingService).rateMovie(any(UUID.class), any(UUID.class), anyInt());

        mockMvc.perform(post("/api/user/ratings")
                        .with(TestSecurityUtil.testUser()).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void getMovieRating_notRated_returnsNotFound() throws Exception {
        UUID movieId = UUID.randomUUID();
        doThrow(new RatingNotFoundException()).when(movieRatingService).getUserRating(any(UUID.class), eq(movieId));

        mockMvc.perform(get("/api/user/ratings/" + movieId)
                        .with(TestSecurityUtil.testUser()).with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("User has not rated this movie yet"));
    }

    @Test
    void rateMovie_unauthenticated_returnsUnauthorized() throws Exception {
        String requestJson = """
            {"movieId":"%s","rating":8}
            """.formatted(UUID.randomUUID());

        mockMvc.perform(post("/api/user/ratings")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isUnauthorized());
    }
}
