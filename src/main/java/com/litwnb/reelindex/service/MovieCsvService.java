package com.litwnb.reelindex.service;

import com.litwnb.reelindex.model.MovieCSV;

import java.io.InputStream;
import java.util.List;

public interface MovieCsvService {
    List<MovieCSV> convertCSV(InputStream inputStream);
}
