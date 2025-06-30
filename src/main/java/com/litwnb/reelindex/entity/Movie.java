package com.litwnb.reelindex.entity;

import com.litwnb.reelindex.model.Genre;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
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

    @NotBlank
    private String director;
    private String posterLink;
}
