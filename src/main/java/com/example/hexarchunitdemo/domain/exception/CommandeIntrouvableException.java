package com.example.hexarchunitdemo.domain.exception;

import java.util.UUID;

/**
 * Exception levée lorsqu'une commande demandée n'existe pas.
 */
public class CommandeIntrouvableException extends RuntimeException {

    private final UUID identifiantCommande;

    public CommandeIntrouvableException(UUID identifiantCommande) {
        super("Aucune commande ne correspond à l'identifiant fourni.");
        this.identifiantCommande = identifiantCommande;
    }

    public UUID identifiantCommande() {
        return identifiantCommande;
    }
}

