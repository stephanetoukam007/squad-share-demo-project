package com.example.hexarchunitdemo.domain.port.out;

import com.example.hexarchunitdemo.domain.model.Commande;

import java.util.Optional;
import java.util.UUID;

/**
 * Port sortant pour la persistance des commandes.
 */
public interface CommandePort {

    Commande save(Commande commande);

    Optional<Commande> findById(UUID identifiantCommande);
}

