package com.litwnb.reelindex.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
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
@Builder
@ToString
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "CHAR(36)", nullable = false)
    private UUID id;

    @NotBlank
    private String username;
    private String password;

    @Builder.Default
    @ManyToMany @Fetch(FetchMode.JOIN)
    @JoinTable(name = "user_watchlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn (name = "movie_id"))
    private Set<Movie> watchlist = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true) @Fetch(value = FetchMode.JOIN)
    private Set<MovieRating> ratings = new HashSet<>();
}
