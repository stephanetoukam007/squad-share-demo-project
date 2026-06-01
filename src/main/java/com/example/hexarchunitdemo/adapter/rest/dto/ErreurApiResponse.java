package com.example.hexarchunitdemo.adapter.rest.dto;

import java.util.Objects;

/**
 * Structure standardisée décrivant une erreur API.
 */
public record ErreurApiResponse(String code, String detail) {

    public ErreurApiResponse {
        Objects.requireNonNull(code, "code");
        Objects.requireNonNull(detail, "detail");
    }
}

