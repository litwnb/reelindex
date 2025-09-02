package com.litwnb.reelindex.bootstrap;

import com.litwnb.reelindex.entity.Movie;
import com.litwnb.reelindex.model.Genre;
import com.litwnb.reelindex.model.MovieCSV;
import com.litwnb.reelindex.repository.MovieRepository;
import com.litwnb.reelindex.service.MovieCsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final MovieRepository movieRepository;
    private final MovieCsvService movieCsvService;

    @Override
    public void run(String... args) throws Exception {
        loadCsvData();
    }

    private void loadCsvData() {
        if (movieRepository.count() == 0) {
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("csvdata/top-movies.csv")) {
                if (inputStream == null) {
                    throw new RuntimeException("CSV file not found");
                }

                List<MovieCSV> records = movieCsvService.convertCSV(inputStream);

                records.forEach(movieCSV -> {
                    Genre genre = switch (Arrays.stream(movieCSV.getGenre().split(","))
                            .findFirst().orElseThrow()) {
                        case "Adventure" -> Genre.ADVENTURE;
                        case "Action" -> Genre.ACTION;
                        case "Biography" -> Genre.BIOGRAPHY;
                        case "Comedy" -> Genre.COMEDY;
                        case "Crime" -> Genre.CRIME;
                        case "Family" -> Genre.FAMILY;
                        case "Fantasy" -> Genre.FANTASY;
                        case "History" -> Genre.HISTORY;
                        case "Horror" -> Genre.HORROR;
                        case "Music" -> Genre.MUSIC;
                        case "Mystery" -> Genre.MYSTERY;
                        case "Noir" -> Genre.NOIR;
                        case "Romance" -> Genre.ROMANCE;
                        case "Sci-Fi" -> Genre.SCI_FI;
                        case "Sport" -> Genre.SPORT;
                        case "Thriller" -> Genre.THRILLER;
                        case "War" -> Genre.WAR;
                        case "Western" -> Genre.WESTERN;
                        default -> Genre.DRAMA;
                    };

                    movieRepository.save(Movie.builder()
                            .title(movieCSV.getSeriesTitle())
                            .releaseYear(movieCSV.getReleasedYear())
                            .runtime(movieCSV.getRuntime())
                            .genre(genre)
                            .overview(movieCSV.getOverview())
                            .imdbScore(movieCSV.getRating())
                            .metaScore(movieCSV.getMetaScore())
                            .director(movieCSV.getDirector())
                            .posterLink(movieCSV.getPosterLink())
                            .build());
                });
            } catch (Exception e) {
                throw new RuntimeException("Failed to load CSV data", e);
            }
        }
    }
}
