# Démo Spring Boot — Architecture hexagonale protégée par ArchUnit

## Objectif

Ceci est un projet de demo pour montrer comment organiser une application selon les principes de l'architecture hexagonale et comment protéger cette organisation avec des tests ArchUnit.:

- `adapter.rest` pour l'exposition HTTP ;
- `adapter.persistence` pour les implémentations techniques ;
- `application.config` pour le wiring ;
- `domain.model`, `domain.port`, `domain.service` pour le coeur métier.

L'ensemble est volontairement écrit avec un vocabulaire métier majoritairement en français afin de rendre l'exemple plus lisible.

## Structure

```text
com.example.hexarchunitdemo
├── adapter
│   ├── persistence
│   └── rest
├── application
│   └── config
└── domain
    ├── exception
    ├── model
    ├── port
    │   ├── in
    │   └── out
    └── service
```

## Cas métier de la démo

La démo expose une gestion simplifiée de commandes :

- enregistrer une commande ;
- consulter une commande ;
- valider quelques règles métier simples ;
- retourner des réponses API standardisées.

## Lancer les tests

```bash
mvn clean test
```

## Lancer l'application

```bash
mvn spring-boot:run
```

## Essayer l'API

Créer une commande :

```bash
curl -X POST http://localhost:8080/api/v1/commandes \
  -H "Content-Type: application/json" \
  -d '{"nomClient":"Sophie Martin","montant":125.50,"devise":"CAD"}'
```

Consulter une commande :

```bash
curl http://localhost:8080/api/v1/commandes/<uuid>
```

## Tests inclus

### Architecture

- `HexagonalArchitectureTest`
- `OnionArchitectureDocumentationTest`
- `GeneralCodingRulesTest`

### Métier / adaptateurs

- `CommandeDomainServiceTest`
- `CommandePersistenceAdapterTest`
- `CommandeControllerTest`

## Gestion des dépendances avec Renovate

Le projet inclut un fichier `renovate.json` pour automatiser les mises à jour de dépendances de manière contrôlée :

- configuration de base `config:recommended` ;
- fuseau horaire `America/Montreal` ;
- `Dependency Dashboard` activé ;
- labels par défaut : `dependencies`, `renovate` ;
- limite de création de PR : `2` par heure et `5` PR ouvertes en parallèle ;
- rebase automatique uniquement en cas de conflit (`rebaseWhen: conflicted`).

### Règles principales

- les mises à jour **majeures** nécessitent une validation depuis le Dependency Dashboard ;
- les dépendances Spring (`org.springframework:*`, `org.springframework.boot:*`) sont regroupées dans la PR `Spring ecosystem` ;
- les dépendances ArchUnit (`com.tngtech.archunit:*`) sont regroupées dans la PR `ArchUnit` ;
- les dépendances de workflows GitHub Actions (si présentes) sont regroupées dans la PR `GitHub Actions`.

## Documentation détaillée

Une documentation plus complète est disponible ici :

- `docs/architecture-hexagonale.md`

## Idée clé

ArchUnit ne remplace pas les tests unitaires ni les tests d'intégration : il protège les décisions d'architecture pour éviter l'érosion progressive du design.
