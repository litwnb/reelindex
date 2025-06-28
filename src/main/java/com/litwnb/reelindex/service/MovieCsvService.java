package com.litwnb.reelindex.service;

import com.litwnb.reelindex.model.MovieCSV;

import java.io.File;
import java.util.List;

public interface MovieCsvService {
    List<MovieCSV> convertCSV(File file);
}
