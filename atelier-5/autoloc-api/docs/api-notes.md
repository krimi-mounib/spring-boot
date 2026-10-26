# Atelier 5 — Documentation de l'API REST

## A. Endpoints exposés

Les contrôleurs dépendent uniquement des interfaces `I...Service` et n'accèdent pas directement aux repositories.

| Entité | Verbe / route | Service appelé | Succès | Erreurs attendues |
|---|---|---|---|---|
| Agence | `GET /api/agences` | `IAgenceService.findAll` | 200 | — |
| Agence | `GET /api/agences/{id}` | `findById` | 200 | 404 |
| Agence | `POST /api/agences` | `create` | 201 | 400, 409 |
| Agence | `PUT /api/agences/{id}` | `update` | 200 | 400, 404, 409 |
| Agence | `DELETE /api/agences/{id}` | `deleteById` | 204 | 404, 409 |
| Véhicule | `GET /api/vehicules` | `IVehiculeService.findAll` | 200 | — |
| Véhicule | `GET /api/vehicules/{id}` | `findById` | 200 | 404 |
| Véhicule | `POST /api/vehicules` | `create` | 201 | 400, 409 |
| Véhicule | `PUT /api/vehicules/{id}` | `update` | 200 | 400, 404, 409 |
| Véhicule | `DELETE /api/vehicules/{id}` | `deleteById` | 204 | 404, 409 |
| Client | `GET /api/clients` | `IClientService.findAll` | 200 | — |
| Client | `GET /api/clients/{id}` | `findById` | 200 | 404 |
| Client | `POST /api/clients` | `create` | 201 | 400, 409 |
| Client | `PUT /api/clients/{id}` | `update` | 200 | 400, 404, 409 |
| Client | `DELETE /api/clients/{id}` | `deleteById` | 204 | 404, 409 |
| Employé | `GET /api/employes` | `IEmployeService.findAll` | 200 | — |
| Employé | `GET /api/employes/{id}` | `findById` | 200 | 404 |
| Employé | `POST /api/employes` | `create` | 201 | 400, 409 |
| Employé | `PUT /api/employes/{id}` | `update` | 200 | 400, 404, 409 |
| Employé | `DELETE /api/employes/{id}` | `deleteById` | 204 | 404, 409 |
| Équipement | `GET /api/equipements` | `IEquipementService.findAll` | 200 | — |
| Équipement | `GET /api/equipements/{id}` | `findById` | 200 | 404 |
| Équipement | `POST /api/equipements` | `create` | 201 | 400, 409 |
| Équipement | `PUT /api/equipements/{id}` | `update` | 200 | 400, 404, 409 |
| Équipement | `DELETE /api/equipements/{id}` | `deleteById` | 204 | 404, 409 |
| Réservation | `GET /api/reservations` | `IReservationService.findAll` | 200 | — |
| Réservation | `GET /api/reservations/{id}` | `findById` | 200 | 404 |
| Réservation | `POST /api/reservations` | `create` | 201 | 400, 409 |
| Réservation | `PUT /api/reservations/{id}` | `update` | 200 | 400, 404, 409 |
| Réservation | `DELETE /api/reservations/{id}` | `deleteById` | 204 | 404, 409 |
| Contrat | `GET /api/contrats` | `IContratService.findAll` | 200 | — |
| Contrat | `GET /api/contrats/{id}` | `findById` | 200 | 404 |
| Contrat | `POST /api/contrats` | `create` | 201 | 400, 409 |
| Contrat | `PUT /api/contrats/{id}` | `update` | 200 | 400, 404, 409 |
| Contrat | `DELETE /api/contrats/{id}` | `deleteById` | 204 | 404, 409 |
| Maintenance | `GET /api/maintenances` | `IMaintenanceService.findAll` | 200 | — |
| Maintenance | `GET /api/maintenances/{id}` | `findById` | 200 | 404 |
| Maintenance | `POST /api/maintenances` | `create` | 201 | 400, 409 |
| Maintenance | `PUT /api/maintenances/{id}` | `update` | 200 | 400, 404, 409 |
| Maintenance | `DELETE /api/maintenances/{id}` | `deleteById` | 204 | 404, 409 |
| Paiement | `GET /api/paiements` | `IPaiementService.findAll` | 200 | — |
| Paiement | `GET /api/paiements/{id}` | `findById` | 200 | 404 |
| Paiement | `POST /api/paiements` | aucune route d'écriture | 405 | — |

Le préfixe `/api` est fourni par `server.servlet.context-path=/api` ; les contrôleurs n'ajoutent pas ce préfixe dans leurs mappings.

## B. Annotations Jackson

- Les collections inverses `Agence.vehicules`, `Agence.employes`, `Vehicule.equipements`, `Vehicule.reservations`, `Equipement.vehicules` et `Client.reservations` utilisent `@JsonIgnore` pour éviter les boucles et garder des réponses REST lisibles.
- `Contrat.paiements` utilise `@JsonManagedReference("contrat-paiements")` et `Paiement.contrat` utilise le `@JsonBackReference` de même nom : le parent sérialise ses paiements, l'enfant ne sérialise pas à nouveau son contrat.
- `Reservation.contrat` utilise `@JsonIgnore` pour éviter le cycle `Contrat.reservation` → `Reservation.contrat` → `Contrat`. Le contrat reste l'endroit depuis lequel la réservation peut être référencée dans le JSON.
- `@JsonIgnore` retire une propriété de la lecture/écriture JSON ; `@JsonBackReference` marque le côté inverse d'une paire parent/enfant et complète `@JsonManagedReference`.

Les associations `LAZY` peuvent exposer des proxies Hibernate. La configuration `spring.jackson.serialization.fail-on-empty-beans=false` est un palliatif temporaire ; des DTO réduiraient le couplage API/base et sont préférables à long terme.

## C. Postman et Swagger

Postman sert à rejouer des requêtes concrètes, vérifier les statuts et garder une collection de scénarios. Exemple : POST agence doit répondre 201 ; un nom obligatoire vide doit répondre 400 ; GET avec un identifiant inexistant doit répondre 404.

Swagger UI présente la documentation produite par SpringDoc et permet d'exécuter les requêtes depuis le navigateur. La spécification JSON est disponible à `/api/v3/api-docs`.

## D. Anomalies et améliorations qualité

- Injection par attribut : remplacée dans les contrôleurs et services par injection constructeur (`@RequiredArgsConstructor`, champs `final`) ; plus simple à tester et moins de dépendances cachées.
- Associations JPA bidirectionnelles : risque de récursion JSON ; corrigé via les annotations Jackson listées en B.
- Conflits SQL (unicité, clés étrangères) : le `GlobalExceptionHandler` renvoie désormais 409 avec un message client lisible.
- Entités exposées directement : autorisé pour l'atelier mais il révèle le modèle de persistance ; DTO recommandé pour une API stable.

L'analyse SonarQube for IDE dépend de la version du plugin et du projet dans IntelliJ. Les points ci-dessus sont les améliorations de code identifiées, pas une prétention de résultat Sonar généré dans cet environnement.

## E. Questions de compréhension

1. `DispatcherServlet` reçoit les requêtes HTTP ; le `HandlerMapping` les associe au contrôleur selon la route et le verbe.
2. `ContratController` dépend de `IContratService` pour conserver les couches et réduire le couplage à une implémentation concrète ou au repository.
3. POST renvoie 201 pour signaler une création, DELETE renvoie 204 après suppression sans corps. Une ressource absente est 404 car la requête est valide mais la ressource n'existe pas ; 500 indique une erreur interne inattendue.
4. Paiement est lecture seule dans le périmètre de l'atelier car la création se fait par cascade depuis Contrat ; POST `/api/paiements` n'est pas mappé et renvoie 405.
5. L'exposition directe des entités montre des champs de persistance, peut provoquer des boucles JSON et révéler des proxies `LAZY`. Les références Jackson évitent les cycles connus ; un DTO délimiterait proprement le contrat HTTP.

## Exemples utiles

Créer une agence (`POST http://localhost:8081/api/agences`):

```json
{"nom":"Agence Centre","ville":"Tunis","adresse":"Avenue Habib Bourguiba","telephone":"71123456"}
```

Créer un véhicule (`POST http://localhost:8081/api/vehicules`), en adaptant `idAgence` à une agence créée :

```json
{"immatriculation":"123TU4567","marque":"Toyota","modele":"Yaris","categorie":"CITADINE","tarifJournalier":120.00,"statut":"DISPONIBLE","agence":{"idAgence":1}}
```

## Atelier 6 — Mission 1 (ajout dans ce projet)

`POST /api/simulations-prix` appelle le service de simulation qui charge une réservation via `IReservationService`, puis délègue les règles à `ICalculateurPrixService`. Le calculateur est sans effets de bord. Voir `docs/atelier6-mission1.md` et `README-DEMARRAGE-TESTS.md`.
