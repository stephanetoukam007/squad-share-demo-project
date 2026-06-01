package com.example.hexarchunitdemo.adapter.rest.dto;

import com.example.hexarchunitdemo.domain.model.StatutCommande;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * DTO sortant décrivant une commande enregistrée.
 */
public record CommandeResponse(UUID id, String nomClient, BigDecimal montant, String devise,
                               StatutCommande statut, Instant dateCreation) {

    public CommandeResponse {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(nomClient, "nomClient");
        Objects.requireNonNull(montant, "montant");
        Objects.requireNonNull(devise, "devise");
        Objects.requireNonNull(statut, "statut");
        Objects.requireNonNull(dateCreation, "dateCreation");
    }
}

