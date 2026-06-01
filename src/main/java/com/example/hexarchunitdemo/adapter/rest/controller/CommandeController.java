package com.example.hexarchunitdemo.adapter.rest.controller;

import com.example.hexarchunitdemo.adapter.rest.CommandeApi;
import com.example.hexarchunitdemo.adapter.rest.dto.CommandeResponse;
import com.example.hexarchunitdemo.adapter.rest.dto.EnregistrerCommandeRequest;
import com.example.hexarchunitdemo.adapter.rest.dto.ReponseApi;
import com.example.hexarchunitdemo.adapter.rest.mapper.CommandeMapper;
import com.example.hexarchunitdemo.domain.exception.CommandeIntrouvableException;
import com.example.hexarchunitdemo.domain.model.Commande;
import com.example.hexarchunitdemo.domain.port.in.facade.CommandesFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.UUID;

/**
 * Contrôleur REST pour l'enregistrement et la consultation des commandes.
 */
@RestController
public class CommandeController implements CommandeApi {

    private final CommandesFacade commandesFacade;
    private final CommandeMapper commandeMapper;

    public CommandeController(CommandesFacade commandesFacade, CommandeMapper commandeMapper) {
        this.commandesFacade = Objects.requireNonNull(commandesFacade, "commandesFacade");
        this.commandeMapper = Objects.requireNonNull(commandeMapper, "commandeMapper");
    }

    @Override
    public ResponseEntity<ReponseApi<CommandeResponse>> enregistrerCommande(EnregistrerCommandeRequest requete) {
        Commande commandeEnregistree = commandesFacade.enregistrerCommande(commandeMapper.versCommande(requete));
        CommandeResponse reponse = commandeMapper.versReponse(commandeEnregistree);
        return ResponseEntity.created(URI.create("/api/v1/commandes/" + reponse.id()))
                .body(ReponseApi.succes(reponse, "Commande enregistrée avec succès"));
    }

    @Override
    public ResponseEntity<ReponseApi<CommandeResponse>> trouverCommande(UUID commandeId) {
        Commande commande = commandesFacade.trouverCommandeParId(commandeId)
                .orElseThrow(() -> new CommandeIntrouvableException(commandeId));
        return ResponseEntity.ok(ReponseApi.succes(commandeMapper.versReponse(commande), "Commande retrouvée avec succès"));
    }
}

