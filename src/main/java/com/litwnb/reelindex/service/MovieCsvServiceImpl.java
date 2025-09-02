package com.litwnb.reelindex.service;

import com.litwnb.reelindex.model.MovieCSV;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class MovieCsvServiceImpl implements MovieCsvService {

    @Override
    public List<MovieCSV> convertCSV(InputStream inputStream) {
        try (Reader reader = new InputStreamReader(inputStream)) {
            return new CsvToBeanBuilder<MovieCSV>(reader)
                    .withType(MovieCSV.class)
                    .build()
                    .parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
