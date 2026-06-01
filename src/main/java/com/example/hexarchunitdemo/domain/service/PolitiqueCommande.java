package com.example.hexarchunitdemo.domain.service;

import com.example.hexarchunitdemo.domain.model.Montant;

import java.math.BigDecimal;

/**
 * Politique métier de validation d'une commande.
 */
public class PolitiqueCommande {

    private static final BigDecimal MONTANT_MAXIMAL = new BigDecimal("10000.00");

    public void valider(Montant montant) {
        if (montant.valeur().compareTo(MONTANT_MAXIMAL) > 0) {
            throw new IllegalArgumentException("Le montant de la commande dépasse la limite autorisée.");
        }
    }
}

