package com.litwnb.reelindex.entity;

import com.litwnb.reelindex.model.Genre;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Movie {
    @Id
    @GeneratedValue(generator = "UUID")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "CHAR(36)", nullable = false)
    private UUID id;

    @NotBlank
    @Size(max = 100)
    @Column(length = 100)
    private String title;

    @NotNull
    private Integer releaseYear;

    @NotBlank
    private String runtime;

    @NotNull
    @JdbcTypeCode(value = SqlTypes.SMALLINT)
    private Genre genre;

    @Column(length = 500)
    private String overview;

    private Float imdbScore;
    private Integer metaScore;
    private double averageRating;

    @NotBlank
    private String director;
    private String posterLink;

    @Builder.Default
    @OneToMany(mappedBy = "movie",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<MovieRating> ratings = new HashSet<>();
}
