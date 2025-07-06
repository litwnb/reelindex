package com.litwnb.reelindex.util;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MovieErrorResponse {
    @NotNull
    private final String message;
    @NotNull
    private final long timestamp;
}
