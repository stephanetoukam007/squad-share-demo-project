package com.example.hexarchunitdemo.domain.port.in;

import com.example.hexarchunitdemo.domain.model.Commande;

import java.util.Optional;
import java.util.UUID;

/**
 * Cas d'usage pour retrouver une commande existante.
 */
public interface TrouverCommandeUseCase {

    Optional<Commande> trouverCommandeParId(UUID identifiantCommande);
}

