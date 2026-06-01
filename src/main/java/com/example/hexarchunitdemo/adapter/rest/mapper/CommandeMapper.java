package com.example.hexarchunitdemo.adapter.rest.mapper;

import com.example.hexarchunitdemo.adapter.rest.dto.CommandeResponse;
import com.example.hexarchunitdemo.adapter.rest.dto.EnregistrerCommandeRequest;
import com.example.hexarchunitdemo.domain.model.Commande;
import com.example.hexarchunitdemo.domain.model.Montant;
import com.example.hexarchunitdemo.domain.port.in.command.EnregistrerCommandeCommand;
import org.springframework.stereotype.Component;

import java.util.Currency;
import java.util.Locale;

/**
 * Mapper entre le contrat HTTP et le modèle métier.
 */
@Component
public class CommandeMapper {

    public EnregistrerCommandeCommand versCommande(EnregistrerCommandeRequest requete) {
        Currency devise = Currency.getInstance(requete.devise().toUpperCase(Locale.ROOT));
        Montant montant = new Montant(requete.montant(), devise);
        return new EnregistrerCommandeCommand(requete.nomClient(), montant);
    }

    public CommandeResponse versReponse(Commande commande) {
        return new CommandeResponse(
                commande.id(),
                commande.nomClient(),
                commande.montantTotal().valeur(),
                commande.montantTotal().devise().getCurrencyCode(),
                commande.statut(),
                commande.dateCreation()
        );
    }
}

