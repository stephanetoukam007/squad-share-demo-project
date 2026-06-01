package com.example.hexarchunitdemo.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Agrégat métier représentant une commande.
 */
public final class Commande {

    private final UUID id;
    private final String nomClient;
    private final Montant montantTotal;
    private final Instant dateCreation;
    private StatutCommande statut;

    private Commande(UUID id, String nomClient, Montant montantTotal, Instant dateCreation, StatutCommande statut) {
        this.id = Objects.requireNonNull(id, "id");
        this.nomClient = exigerTexte(nomClient, "Le nom du client est obligatoire.");
        this.montantTotal = Objects.requireNonNull(montantTotal, "montantTotal");
        this.dateCreation = Objects.requireNonNull(dateCreation, "dateCreation");
        this.statut = Objects.requireNonNull(statut, "statut");
    }

    public static Commande enregistrer(String nomClient, Montant montantTotal) {
        return new Commande(UUID.randomUUID(), nomClient, montantTotal, Instant.now(), StatutCommande.BROUILLON);
    }

    public void valider() {
        if (statut == StatutCommande.VALIDEE) {
            throw new IllegalStateException("La commande est déjà validée.");
        }
        statut = StatutCommande.VALIDEE;
    }

    public UUID id() {
        return id;
    }

    public String nomClient() {
        return nomClient;
    }

    public Montant montantTotal() {
        return montantTotal;
    }

    public Instant dateCreation() {
        return dateCreation;
    }

    public StatutCommande statut() {
        return statut;
    }

    private static String exigerTexte(String valeur, String message) {
        if (valeur == null || valeur.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return valeur;
    }
}

