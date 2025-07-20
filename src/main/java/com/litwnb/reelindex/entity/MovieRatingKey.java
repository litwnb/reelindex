package com.litwnb.reelindex.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MovieRatingKey implements Serializable {
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "user_id", columnDefinition = "CHAR(36)", nullable = false)
    private UUID userId;
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "movie_id", columnDefinition = "CHAR(36)", nullable = false)
    private UUID movieId;
}
