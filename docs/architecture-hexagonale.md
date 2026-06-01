# Documentation de l'architecture hexagonale

## 1. Intention

Cette démo reprend la logique d'organisation d'un projet en architecture hexagonale en l'appliquant à un domaine réduit : la gestion de commandes.

L'objectif est double :

- montrer une structure hexagonale lisible ;
- démontrer comment protéger cette structure avec des tests ArchUnit et des tests fonctionnels.

## 2. Découpage des couches

```text
com.example.hexarchunitdemo
├── adapter
│   ├── persistence
│   │   └── CommandePersistenceAdapter
│   └── rest
│       ├── controller
│       │   ├── CommandeController
│       │   └── GestionnaireGlobalExceptions
│       ├── dto
│       │   ├── CommandeResponse
│       │   ├── EnregistrerCommandeRequest
│       │   ├── ErreurApiResponse
│       │   └── ReponseApi
│       ├── mapper
│       │   └── CommandeMapper
│       └── CommandeApi
├── application
│   └── config
│       └── ApplicationConfiguration
└── domain
    ├── exception
    │   └── CommandeIntrouvableException
    ├── model
    │   ├── Commande
    │   ├── Montant
    │   └── StatutCommande
    ├── port
    │   ├── in
    │   │   ├── command
    │   │   │   └── EnregistrerCommandeCommand
    │   │   ├── facade
    │   │   │   └── CommandesFacade
    │   │   ├── EnregistrerCommandeUseCase
    │   │   └── TrouverCommandeUseCase
    │   └── out
    │       └── CommandePort
    └── service
        ├── CommandeDomainService
        └── PolitiqueCommande
```

## 3. Rôle de chaque couche

### Domaine

Le package `domain` contient tout ce qui porte le métier pur :

- `Commande` est l'agrégat principal ;
- `Montant` est un value object ;
- `PolitiqueCommande` exprime une règle métier ;
- `CommandeDomainService` orchestre les cas d'usage ;
- `CommandePort` décrit la dépendance sortante nécessaire à la persistance.

Le domaine ne dépend ni de Spring, ni de la couche REST, ni de la persistence.

### Application

Le package `application.config` contient le wiring :

- création de `PolitiqueCommande` ;
- assemblage de `CommandeDomainService` derrière la façade `CommandesFacade`.

Cette couche relie les abstractions du coeur métier aux implémentations fournies par les adaptateurs.

### Adaptateurs

#### `adapter.rest`

Cette couche traduit le protocole HTTP vers les cas d'usage du domaine.

- `CommandeApi` expose le contrat HTTP ;
- `CommandeController` implémente ce contrat ;
- `CommandeMapper` convertit le DTO entrant en commande métier et inversement ;
- `GestionnaireGlobalExceptions` standardise les erreurs.

#### `adapter.persistence`

Cette couche implémente le port sortant `CommandePort`.

Dans cette démo, la persistence est volontairement en mémoire (`ConcurrentHashMap`) afin de rester simple.

## 4. Flux d'une requête

### Enregistrement d'une commande

1. Une requête HTTP `POST /api/v1/commandes` arrive dans `CommandeController`.
2. Le contrôleur délègue au `CommandeMapper` pour transformer `EnregistrerCommandeRequest` en `EnregistrerCommandeCommand`.
3. La façade `CommandesFacade` transmet au `CommandeDomainService`.
4. Le service applique `PolitiqueCommande`.
5. Le service crée l'agrégat `Commande`, le valide, puis le sauvegarde via `CommandePort`.
6. `CommandePersistenceAdapter` persiste en mémoire.
7. Le contrôleur renvoie une `ReponseApi<CommandeResponse>`.

### Consultation d'une commande

1. Une requête HTTP `GET /api/v1/commandes/{id}` arrive dans `CommandeController`.
2. Le contrôleur appelle `CommandesFacade`.
3. Le service délègue au port `CommandePort`.
4. Si rien n'est trouvé, une `CommandeIntrouvableException` est levée.
5. `GestionnaireGlobalExceptions` transforme cette erreur en réponse HTTP 404 standardisée.

## 5. Pourquoi cette structure est utile

- elle sépare clairement le métier de la technique ;
- elle rend les dépendances explicites via les ports ;
- elle facilite les tests unitaires et les tests d'architecture ;
- elle permet de remplacer un adaptateur sans toucher au coeur métier.

## 6. Tests présents

### Tests d'architecture

Dans `src/test/java/com/example/hexarchunitdemo/architecture` :

- `HexagonalArchitectureTest` protège les règles de dépendances ;
- `OnionArchitectureDocumentationTest` documente et vérifie l'architecture en oignon ;
- `GeneralCodingRulesTest` applique des règles générales ArchUnit.

### Tests métiers et techniques

- `CommandeDomainServiceTest` vérifie le coeur métier ;
- `CommandePersistenceAdapterTest` vérifie l'adaptateur sortant ;
- `CommandeControllerTest` vérifie le contrat HTTP et les réponses standardisées.

## 7. Correspondance avec le projet de référence

Cette démo reprend volontairement les mêmes idées de structuration :

- `adapter.rest` pour les contrôleurs et DTO ;
- `adapter.persistence` pour les implémentations techniques ;
- `application.config` pour l'assemblage ;
- `domain.port.in` et `domain.port.out` pour les ports ;
- `domain.service` pour l'orchestration métier.

La principale différence est simplement le domaine métier : ici nous parlons de commandes plutôt que de demandes parents ou de répétiteurs.

