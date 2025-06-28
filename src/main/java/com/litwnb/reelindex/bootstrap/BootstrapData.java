package com.litwnb.reelindex.bootstrap;

import com.litwnb.reelindex.entity.Movie;
import com.litwnb.reelindex.model.Genre;
import com.litwnb.reelindex.model.MovieCSV;
import com.litwnb.reelindex.repository.MovieRepository;
import com.litwnb.reelindex.service.MovieCsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
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

    private void loadCsvData() throws FileNotFoundException {
        if (movieRepository.count() == 0) {
            File csvFile = ResourceUtils.getFile("classpath:csvdata/top-movies.csv");
            List<MovieCSV> records = movieCsvService.convertCSV(csvFile);

            records.forEach(movieCSV -> {
                Genre genre = switch (Arrays.stream(movieCSV.getGenre().split(",")).findFirst().get()) {
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
                                .metaScore(movieCSV.getMetaScore())
                                .director(movieCSV.getDirector())
                                .posterLink(movieCSV.getPosterLink())
                        .build());
            });

        }
    }
}
