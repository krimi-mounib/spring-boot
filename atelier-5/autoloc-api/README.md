# AutoLoc — Atelier 5 : API REST et Swagger

Cette version reprend la couche Service de l’Atelier 4 et expose les neuf entités via des contrôleurs REST. Les contrôleurs injectent les interfaces `I...Service`. Tous les contrôleurs CRUD exposent GET liste, GET détail, POST, PUT et DELETE, sauf `PaiementController`, en lecture seule.

## Lancer
1. Installer/configurer MySQL et la base `autoloc_db`.
2. Configurer `DB_USERNAME` et `DB_PASSWORD` si besoin (défauts : `root` et mot de passe vide).
3. Ouvrir ce dossier comme projet Maven dans IntelliJ, puis exécuter `./mvnw spring-boot:run` (Linux/macOS) ou `mvn spring-boot:run` (Windows avec Maven dans le PATH).
4. Base URL : `http://localhost:8080/api`.

## Documentation et requêtes
- Swagger UI : `http://localhost:8080/api/swagger-ui.html`
- OpenAPI JSON : `http://localhost:8080/api/v3/api-docs`
- Collection Postman : `postman/AutoLoc.postman_collection.json`
- Environnement Postman : `postman/AutoLoc-local.postman_environment.json`
- Notes : `docs/api-notes.md`

## Règles importantes
- Les identifiants suivent le modèle existant : `idAgence`, `idVehicule`, `idContrat`, etc.
- Créer les parents avant les dépendants puis utiliser leurs identifiants dans les corps JSON.
- `Contrat.paiements` utilise `CascadeType.ALL` et `orphanRemoval = true` ; le paiement est créé via le contrat, pas par POST paiement.
- Le modèle source n’inclut ni quantité Équipement, ni coût Maintenance, ni kilométrage Véhicule.
- Les entités JPA sont exposées directement pour l’exercice ; des DTOs sont préférables en production.
