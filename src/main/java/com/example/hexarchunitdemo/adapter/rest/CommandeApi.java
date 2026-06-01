package com.example.hexarchunitdemo.adapter.rest;

import com.example.hexarchunitdemo.adapter.rest.dto.CommandeResponse;
import com.example.hexarchunitdemo.adapter.rest.dto.EnregistrerCommandeRequest;
import com.example.hexarchunitdemo.adapter.rest.dto.ReponseApi;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

/**
 * Contrat HTTP exposant les opérations sur les commandes.
 */
@RequestMapping("/api/v1/commandes")
public interface CommandeApi {

    @PostMapping
    ResponseEntity<ReponseApi<CommandeResponse>> enregistrerCommande(@Valid @RequestBody EnregistrerCommandeRequest requete);

    @GetMapping("/{commandeId}")
    ResponseEntity<ReponseApi<CommandeResponse>> trouverCommande(@PathVariable UUID commandeId);
}

