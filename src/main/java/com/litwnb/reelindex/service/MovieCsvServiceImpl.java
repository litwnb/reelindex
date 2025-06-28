package com.litwnb.reelindex.service;

import com.litwnb.reelindex.model.MovieCSV;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
public class MovieCsvServiceImpl implements MovieCsvService {

    @Override
    public List<MovieCSV> convertCSV(File csvFile) {
        try {
            return new CsvToBeanBuilder<MovieCSV>(new FileReader(csvFile))
                    .withType(MovieCSV.class)
                    .build().parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
