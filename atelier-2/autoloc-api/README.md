# AutoLoc — Atelier 2

Version centrée sur les associations JPA, sans couche Service ni API REST, conformément à la progression des ateliers.

## Lancer
1. Configurer MySQL et la base `autoloc_db` (ou ajuster `src/main/resources/application.properties`).
2. Définir `DB_USERNAME` et `DB_PASSWORD` si nécessaire.
3. Ouvrir ce dossier comme projet Maven dans IntelliJ, puis lancer `./mvnw spring-boot:run` (Linux/macOS) ou `mvn spring-boot:run` (Windows avec Maven dans le PATH).
4. Consulter `docs/atelier2-notes.md` pour les associations, cascade et fetch.

Package conservé du projet source : `tn.esprit.mohamedmounibkrimicce11.domain`.
