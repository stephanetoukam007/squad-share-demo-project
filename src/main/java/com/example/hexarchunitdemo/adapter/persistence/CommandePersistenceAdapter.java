package com.example.hexarchunitdemo.adapter.persistence;

import com.example.hexarchunitdemo.domain.model.Commande;
import com.example.hexarchunitdemo.domain.port.out.CommandePort;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Adaptateur de persistance en mémoire pour la démonstration.
 */
@Component
public class CommandePersistenceAdapter implements CommandePort {

    private final Map<UUID, Commande> commandes = new ConcurrentHashMap<>();

    @Override
    public Commande save(Commande commande) {
        commandes.put(commande.id(), commande);
        return commande;
    }

    @Override
    public Optional<Commande> findById(UUID identifiantCommande) {
        return Optional.ofNullable(commandes.get(identifiantCommande));
    }
}

