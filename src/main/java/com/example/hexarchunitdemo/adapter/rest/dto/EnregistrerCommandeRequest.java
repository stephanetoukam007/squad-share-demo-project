package com.example.hexarchunitdemo.adapter.rest.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Objects;

/**
 * Payload entrant pour enregistrer une commande.
 */
public record EnregistrerCommandeRequest(
        @NotBlank(message = "Le champ 'nomClient' est obligatoire.") String nomClient,
        @NotNull(message = "Le champ 'montant' est obligatoire.")
        @DecimalMin(value = "0.01", message = "Le champ 'montant' doit être supérieur à 0.00.") BigDecimal montant,
        @NotBlank(message = "Le champ 'devise' est obligatoire.") String devise
) {

    public EnregistrerCommandeRequest {
        Objects.requireNonNull(montant, "montant");
        devise = devise == null ? null : devise.toUpperCase(Locale.ROOT);
    }
}

