package com.example.hexarchunitdemo.application.config;

import com.example.hexarchunitdemo.domain.port.in.facade.CommandesFacade;
import com.example.hexarchunitdemo.domain.port.out.CommandePort;
import com.example.hexarchunitdemo.domain.service.CommandeDomainService;
import com.example.hexarchunitdemo.domain.service.PolitiqueCommande;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration applicative pour le wiring hexagonal.
 */
@Configuration
public class ApplicationConfiguration {

    @Bean
    public PolitiqueCommande politiqueCommande() {
        return new PolitiqueCommande();
    }

    @Bean
    public CommandesFacade commandesFacade(CommandePort commandePort, PolitiqueCommande politiqueCommande) {
        return new CommandeDomainService(commandePort, politiqueCommande);
    }
}

