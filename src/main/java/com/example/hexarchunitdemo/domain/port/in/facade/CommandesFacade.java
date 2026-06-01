package com.example.hexarchunitdemo.domain.port.in.facade;

import com.example.hexarchunitdemo.domain.port.in.EnregistrerCommandeUseCase;
import com.example.hexarchunitdemo.domain.port.in.TrouverCommandeUseCase;

/**
 * Façade regroupant les cas d'usage exposés autour des commandes.
 */
public interface CommandesFacade extends EnregistrerCommandeUseCase, TrouverCommandeUseCase {
}

