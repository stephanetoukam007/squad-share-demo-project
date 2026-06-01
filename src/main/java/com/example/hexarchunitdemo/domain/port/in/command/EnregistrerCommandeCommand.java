package com.example.hexarchunitdemo.domain.port.in.command;

import com.example.hexarchunitdemo.domain.model.Montant;

/**
 * Commande transportant les données nécessaires à l'enregistrement d'une commande.
 */
public record EnregistrerCommandeCommand(String nomClient, Montant montantTotal) {
}

