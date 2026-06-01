package com.example.hexarchunitdemo.adapter.rest.dto;

import java.time.Instant;

/**
 * Enveloppe standard des réponses HTTP du projet.
 */
public record ReponseApi<T>(boolean succes, String message, T donnees, ErreurApiResponse erreur, Instant horodatage) {

    public static <T> ReponseApi<T> succes(T donnees, String message) {
        return new ReponseApi<>(true, message, donnees, null, Instant.now());
    }

    public static <T> ReponseApi<T> echec(String message, ErreurApiResponse erreur) {
        return new ReponseApi<>(false, message, null, erreur, Instant.now());
    }
}

