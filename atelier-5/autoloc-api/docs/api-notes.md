# Atelier 5 — Documentation de l’API REST

Les URL de cette note incluent le context path `/api`, défini une seule fois dans `application.properties`. L’application écoute sur le port `8080`.

## A. Endpoints

| Contrôleur | Verbe et route | Service appelé | Succès | Erreurs principales |
|---|---|---|---|---|
| Agence | `GET /api/agences` | `IAgenceService.findAll` | 200 | — |
| Agence | `GET /api/agences/{id}` | `findById` | 200 | 404 |
| Agence | `POST /api/agences` | `create` | 201 | 400, 409 |
| Agence | `PUT /api/agences/{id}` | `update` | 200 | 400, 404, 409 |
| Agence | `DELETE /api/agences/{id}` | `deleteById` | 204 | 404, 409 |
| Employé | `GET /api/employes` | `IEmployeService.findAll` | 200 | — |
| Employé | `GET /api/employes/{id}` | `findById` | 200 | 404 |
| Employé | `POST /api/employes` | `create` | 201 | 400, 409 |
| Employé | `PUT /api/employes/{id}` | `update` | 200 | 400, 404, 409 |
| Employé | `DELETE /api/employes/{id}` | `deleteById` | 204 | 404, 409 |
| Véhicule | `GET /api/vehicules` | `IVehiculeService.findAll` | 200 | — |
| Véhicule | `GET /api/vehicules/{id}` | `findById` | 200 | 404 |
| Véhicule | `POST /api/vehicules` | `create` | 201 | 400, 409 |
| Véhicule | `PUT /api/vehicules/{id}` | `update` | 200 | 400, 404, 409 |
| Véhicule | `DELETE /api/vehicules/{id}` | `deleteById` | 204 | 404, 409 |
| Équipement | `GET /api/equipements` | `IEquipementService.findAll` | 200 | — |
| Équipement | `GET /api/equipements/{id}` | `findById` | 200 | 404 |
| Équipement | `POST /api/equipements` | `create` | 201 | 400, 409 |
| Équipement | `PUT /api/equipements/{id}` | `update` | 200 | 400, 404, 409 |
| Équipement | `DELETE /api/equipements/{id}` | `deleteById` | 204 | 404, 409 |
| Client | `GET /api/clients` | `IClientService.findAll` | 200 | — |
| Client | `GET /api/clients/{id}` | `findById` | 200 | 404 |
| Client | `POST /api/clients` | `create` | 201 | 400, 409 |
| Client | `PUT /api/clients/{id}` | `update` | 200 | 400, 404, 409 |
| Client | `DELETE /api/clients/{id}` | `deleteById` | 204 | 404, 409 |
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
| Paiement | `GET /api/paiements` | `IPaiementService.findAll` | 200 | — |
| Paiement | `GET /api/paiements/{id}` | `findById` | 200 | 404 |
| Paiement | `POST/PUT/DELETE /api/paiements` | Non exposé | 405 | — |
| Maintenance | `GET /api/maintenances` | `IMaintenanceService.findAll` | 200 | — |
| Maintenance | `GET /api/maintenances/{id}` | `findById` | 200 | 404 |
| Maintenance | `POST /api/maintenances` | `create` | 201 | 400, 409 |
| Maintenance | `PUT /api/maintenances/{id}` | `update` | 200 | 400, 404, 409 |
| Maintenance | `DELETE /api/maintenances/{id}` | `deleteById` | 204 | 404, 409 |

Les identifiants JSON respectent les entités réellement fournies (`idAgence`, `idVehicule`, etc.). `GlobalExceptionHandler` renvoie un corps d’erreur JSON avec `statut`, `message` et `horodatage`. Les absences sont des 404, les erreurs métier des 400 et les conflits d’intégrité des 409.

## B. Annotations Jackson

| Association | Annotation | Justification |
|---|---|---|
| `Contrat.paiements` / `Paiement.contrat` | `@JsonManagedReference("contrat-paiements")` / `@JsonBackReference("contrat-paiements")` | Évite la récursion dans la composition Contrat → Paiement et rattache les paiements au contrat lors de la désérialisation. |
| Collections inverses d’Agence, Client et Véhicule | `@JsonIgnore` | Évite de réinclure les collections qui referment le graphe JSON. |
| Associations `ManyToOne` lazy | `@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})` au besoin | Évite d’exposer les propriétés internes des proxys Hibernate. |
| `Reservation.contrat` | `@JsonIgnore` | Évite le cycle avec `Contrat.reservation`. |

`@JsonBackReference` est la partie inverse d’une paire managed/back reference et permet d’omettre le retour enfant → parent pendant la sérialisation. `@JsonIgnore` exclut une propriété de la sérialisation/désérialisation ; il est adapté aux collections inverses qui n’ont pas besoin d’être créées depuis ce point d’entrée.

## C. Postman et Swagger

Postman rejoue les requêtes HTTP, contrôle les codes et permet d’ajouter des assertions. Swagger UI présente une documentation générée à partir des contrôleurs et permet d’essayer les opérations. Exemple : la collection contient un POST Agence avec `nom` vide qui vérifie la réponse 400, et une requête POST Paiement qui doit recevoir 405.

- Swagger UI : `http://localhost:8080/api/swagger-ui.html`
- OpenAPI JSON : `http://localhost:8080/api/v3/api-docs`
- Collection : `postman/AutoLoc.postman_collection.json`
- Environnement : `postman/AutoLoc-local.postman_environment.json`

## D. SonarQube for IDE

1. Injection par attribut : cache les dépendances et rend les tests hors Spring difficiles ; les services/contrôleurs utilisent un constructeur via `@RequiredArgsConstructor` et des champs `final`.
2. Imports inutilisés : augmentent le bruit ; retirer les imports signalés par l’IDE.
3. Exposition directe des entités JPA : lie le contrat HTTP au schéma de persistance ; les DTOs seraient préférables en production.

Les règles Sonar exactes varient selon la version du plugin. Exécuter l’analyse dans IntelliJ et consigner les anomalies effectivement affichées avant le rendu.

## E. Questions de compréhension

1. `DispatcherServlet` reçoit les requêtes HTTP ; Spring MVC sélectionne la méthode du contrôleur à partir du verbe et du chemin.
2. `ContratController` dépend de `IContratService` pour rester couplé à une abstraction, sans accéder directement au repository ni à l’implémentation.
3. POST renvoie 201 quand une ressource est créée ; DELETE renvoie 204 quand aucune représentation n’est renvoyée. Une ressource absente est une situation 404 connue, pas une erreur inattendue 500.
4. `PaiementController` n’expose que GET : les paiements suivent le cycle de vie du contrat par cascade. `POST /api/paiements` reçoit 405 car aucune méthode POST n’est mappée.
5. Exposer des entités peut divulguer la structure interne et créer des cycles JSON/proxys LAZY. `Contrat.paiements` utilise managed/back reference car il s’agit d’une composition ; `Agence.vehicules` utilise `@JsonIgnore` pour ne pas ajouter la collection inverse aux réponses d’agence.
