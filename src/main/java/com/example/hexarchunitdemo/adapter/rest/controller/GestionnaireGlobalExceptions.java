package com.example.hexarchunitdemo.adapter.rest.controller;

import com.example.hexarchunitdemo.adapter.rest.dto.ErreurApiResponse;
import com.example.hexarchunitdemo.adapter.rest.dto.ReponseApi;
import com.example.hexarchunitdemo.domain.exception.CommandeIntrouvableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * Gestionnaire centralisé des erreurs HTTP.
 */
@RestControllerAdvice
public class GestionnaireGlobalExceptions {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ReponseApi<Void>> gererValidation(MethodArgumentNotValidException exception) {
        String detail = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(erreur -> Objects.requireNonNullElse(erreur.getDefaultMessage(), "La requête envoyée est invalide."))
                .findFirst()
                .orElse("La requête envoyée est invalide.");

        return ResponseEntity.badRequest()
                .body(ReponseApi.echec("La requête est invalide.", new ErreurApiResponse("REQUETE_INVALIDE", detail)));
    }

    @ExceptionHandler(CommandeIntrouvableException.class)
    public ResponseEntity<ReponseApi<Void>> gererCommandeIntrouvable(CommandeIntrouvableException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ReponseApi.echec("La commande demandée est introuvable.",
                        new ErreurApiResponse("COMMANDE_INTROUVABLE", exception.getMessage())));
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<ReponseApi<Void>> gererRegleMetier(RuntimeException exception) {
        return ResponseEntity.badRequest()
                .body(ReponseApi.echec("Une règle métier a été violée.",
                        new ErreurApiResponse("REGLE_METIER_VIOLEE", exception.getMessage())));
    }
}


