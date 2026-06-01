package com.example.hexarchunitdemo.domain.port.in;

import com.example.hexarchunitdemo.domain.model.Commande;
import com.example.hexarchunitdemo.domain.port.in.command.EnregistrerCommandeCommand;

/**
 * Cas d'usage pour l'enregistrement d'une commande.
 */
public interface EnregistrerCommandeUseCase {

    Commande enregistrerCommande(EnregistrerCommandeCommand commande);
}

