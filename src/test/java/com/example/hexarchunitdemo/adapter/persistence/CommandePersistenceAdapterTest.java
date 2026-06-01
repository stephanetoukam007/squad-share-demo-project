package com.example.hexarchunitdemo.adapter.persistence;

import com.example.hexarchunitdemo.domain.model.Commande;
import com.example.hexarchunitdemo.domain.model.Montant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CommandePersistenceAdapterTest {

    private CommandePersistenceAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new CommandePersistenceAdapter();
    }

    @Test
    @DisplayName("Sauvegarde et lecture d'une commande en mémoire")
    void save_QUAND_commandeValide_ALORS_laCommandeEstRetrouvable() {
        Commande commande = Commande.enregistrer("Alice Dupont",
                new Montant(new BigDecimal("49.90"), Currency.getInstance("CAD")));

        adapter.save(commande);
        Optional<Commande> resultat = adapter.findById(commande.id());

        assertThat(resultat).contains(commande);
    }

    @Test
    @DisplayName("Retourne vide quand l'identifiant est inconnu")
    void findById_QUAND_identifiantInconnu_ALORS_retourneOptionalVide() {
        Optional<Commande> resultat = adapter.findById(UUID.randomUUID());

        assertThat(resultat).isEmpty();
    }
}

