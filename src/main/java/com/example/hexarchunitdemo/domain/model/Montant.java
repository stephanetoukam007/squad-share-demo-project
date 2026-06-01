package com.example.hexarchunitdemo.domain.model;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

/**
 * Value object représentant un montant monétaire.
 */
public record Montant(BigDecimal valeur, Currency devise) {

    public Montant {
        Objects.requireNonNull(valeur, "valeur");
        Objects.requireNonNull(devise, "devise");
        if (valeur.signum() <= 0) {
            throw new IllegalArgumentException("Le montant doit être strictement positif.");
        }
    }

    public static Montant enCad(String valeur) {
        return new Montant(new BigDecimal(valeur), Currency.getInstance("CAD"));
    }
}

