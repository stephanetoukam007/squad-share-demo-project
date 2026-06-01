package com.example.hexarchunitdemo.domain.service;

import com.example.hexarchunitdemo.domain.model.Commande;
import com.example.hexarchunitdemo.domain.model.Montant;
import com.example.hexarchunitdemo.domain.model.StatutCommande;
import com.example.hexarchunitdemo.domain.port.in.command.EnregistrerCommandeCommand;
import com.example.hexarchunitdemo.domain.port.out.CommandePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandeDomainServiceTest {

    @Mock
    private CommandePort commandePort;

    private CommandeDomainService service;

    @BeforeEach
    void setUp() {
        service = new CommandeDomainService(commandePort, new PolitiqueCommande());
    }

    private EnregistrerCommandeCommand commandeValide() {
        return new EnregistrerCommandeCommand("Sophie Martin",
                new Montant(new BigDecimal("125.50"), Currency.getInstance("CAD")));
    }

    @Test
    @DisplayName("Enregistrement réussi d'une commande valide")
    void enregistrerCommande_QUAND_commandeValide_ALORS_retourneCommandeValidee() {
        when(commandePort.save(any(Commande.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Commande resultat = service.enregistrerCommande(commandeValide());

        assertThat(resultat.nomClient()).isEqualTo("Sophie Martin");
        assertThat(resultat.montantTotal().valeur()).isEqualByComparingTo("125.50");
        assertThat(resultat.statut()).isEqualTo(StatutCommande.VALIDEE);
        verify(commandePort).save(any(Commande.class));
    }

    @Test
    @DisplayName("Exception si la commande est absente")
    void enregistrerCommande_QUAND_commandeNulle_ALORS_leveUneException() {
        assertThatThrownBy(() -> service.enregistrerCommande(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La commande d'enregistrement est obligatoire.");

        verify(commandePort, never()).save(any());
    }

    @Test
    @DisplayName("Exception si le montant dépasse la limite métier")
    void enregistrerCommande_QUAND_montantTropEleve_ALORS_leveUneException() {
        EnregistrerCommandeCommand commande = new EnregistrerCommandeCommand("Sophie Martin",
                new Montant(new BigDecimal("10000.01"), Currency.getInstance("CAD")));

        assertThatThrownBy(() -> service.enregistrerCommande(commande))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Le montant de la commande dépasse la limite autorisée.");

        verify(commandePort, never()).save(any());
    }

    @Test
    @DisplayName("Recherche d'une commande existante")
    void trouverCommandeParId_QUAND_commandeExiste_ALORS_retourneOptionalRenseigne() {
        Commande commande = Commande.enregistrer("Sophie Martin",
                new Montant(new BigDecimal("99.99"), Currency.getInstance("CAD")));
        UUID identifiantCommande = commande.id();
        when(commandePort.findById(identifiantCommande)).thenReturn(Optional.of(commande));

        Optional<Commande> resultat = service.trouverCommandeParId(identifiantCommande);

        assertThat(resultat).contains(commande);
        verify(commandePort).findById(identifiantCommande);
    }
}

