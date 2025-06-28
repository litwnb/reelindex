package com.litwnb.reelindex.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieCSV {
    @CsvBindByName(column = "Poster_Link")
    private String posterLink;
    @CsvBindByName(column = "Series_Title")
    private String seriesTitle;
    @CsvBindByName(column = "Released_Year")
    private Integer releasedYear;
    @CsvBindByName(column = "Certificate")
    private String certificate;
    @CsvBindByName(column = "Runtime")
    private String runtime;
    @CsvBindByName(column = "Genre")
    private String genre;
    @CsvBindByName(column = "IMDB_Rating")
    private String rating;
    @CsvBindByName(column = "Overview")
    private String overview;
    @CsvBindByName(column = "Meta_score")
    private Float metaScore;
    @CsvBindByName(column = "Director")
    private String director;
    @CsvBindByName(column = "Star1")
    private String star1;
    @CsvBindByName(column = "Star2")
    private String star2;
    @CsvBindByName(column = "Star3")
    private String star3;
    @CsvBindByName(column = "Star4")
    private String star4;
    @CsvBindByName(column = "No_of_Votes")
    private Integer noOfVotes;
    @CsvBindByName(column = "Gross")
    private String gross;
}
