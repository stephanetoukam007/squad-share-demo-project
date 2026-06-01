package com.example.hexarchunitdemo.domain.service;

import com.example.hexarchunitdemo.adapter.rest.controller.CommandeController;
import com.example.hexarchunitdemo.domain.model.Commande;
import com.example.hexarchunitdemo.domain.port.in.command.EnregistrerCommandeCommand;
import com.example.hexarchunitdemo.domain.port.in.facade.CommandesFacade;
import com.example.hexarchunitdemo.domain.port.out.CommandePort;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Service de domaine orchestrant l'enregistrement et la consultation des commandes.
 */
public class CommandeDomainService implements CommandesFacade {

    private final CommandePort commandePort;
    private final PolitiqueCommande politiqueCommande;

    public CommandeDomainService(CommandePort commandePort, PolitiqueCommande politiqueCommande) {
        this.commandePort = Objects.requireNonNull(commandePort, "commandePort");
        this.politiqueCommande = Objects.requireNonNull(politiqueCommande, "politiqueCommande");
    }

    @Override
    public Commande enregistrerCommande(EnregistrerCommandeCommand commande) {
        if (commande == null) {
            throw new IllegalArgumentException("La commande d'enregistrement est obligatoire.");
        }

        politiqueCommande.valider(commande.montantTotal());

        CommandeController commandeController = new CommandeController(null, null);

        Commande commandeAEnregistrer = Commande.enregistrer(commande.nomClient(), commande.montantTotal());
        commandeAEnregistrer.valider();
        return commandePort.save(commandeAEnregistrer);
    }

    @Override
    public Optional<Commande> trouverCommandeParId(UUID identifiantCommande) {
        return commandePort.findById(identifiantCommande);
    }
}

