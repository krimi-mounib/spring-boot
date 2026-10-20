# ASI 26-27 — AutoLoc Spring Boot Ateliers 2, 4 et 5

Ce dépôt contient trois projets Maven autonomes organisés selon la progression des supports d’atelier. Chaque sous-dossier peut être ouvert indépendamment dans IntelliJ.

| Dossier | Périmètre | Livrables |
|---|---|---|
| [`atelier-2/autoloc-api`](atelier-2/autoloc-api/) | Associations JPA | `mappedBy`, cascade, orphan removal, fetch LAZY, notes de validation |
| [`atelier-4/autoloc-api`](atelier-4/autoloc-api/) | Spring IoC / DI et couche Service | 9 interfaces et implémentations de service, validations, exception métier, `docs/service-notes.md` |
| [`atelier-5/autoloc-api`](atelier-5/autoloc-api/) | API REST et SpringDoc | 9 contrôleurs (Paiement en lecture seule), codes HTTP, erreurs JSON, Swagger/OpenAPI, collection Postman |

## Exécution

- Java 17+ et MySQL sont nécessaires. La configuration par défaut utilise la base `autoloc_db`, utilisateur `root` et mot de passe vide. Personnaliser avec `DB_USERNAME` et `DB_PASSWORD`.
- Ouvrir **le dossier de l’atelier souhaité** comme projet Maven dans IntelliJ. Les projets partagent le même nom de base de données : sauvegarder les données avant de passer d’une version à une autre.
- Linux/macOS : depuis le dossier projet, `./mvnw spring-boot:run`. Sous Windows, utiliser `mvn spring-boot:run` (Maven dans le PATH) ou le panneau Maven d’IntelliJ.
- Atelier 5 : Swagger UI à `http://localhost:8080/api/swagger-ui.html`, OpenAPI JSON à `http://localhost:8080/api/v3/api-docs`.

## Vérification avant rendu

Exécuter `./mvnw clean test` dans les trois projets, configurer MySQL, essayer la collection Postman et exécuter SonarQube for IDE dans IntelliJ. Le build Maven n’a pas pu être confirmé dans l’environnement de préparation car Maven Central n’était pas accessible.
