# AutoLoc — Atelier 4

Cette version ajoute la couche Service aux neuf entités. Chaque service utilise une interface `I...Service`, une implémentation `...ServiceImpl`, l’injection par constructeur (`@RequiredArgsConstructor`) et le repository correspondant. Les contrôleurs REST et SpringDoc sont réservés à l’Atelier 5.

## Lancer
1. Configurer MySQL et `DB_USERNAME` / `DB_PASSWORD` si nécessaire.
2. Ouvrir ce dossier comme projet Maven dans IntelliJ.
3. Lancer `./mvnw spring-boot:run` (Linux/macOS) ou `mvn spring-boot:run` (Windows avec Maven dans le PATH).
4. Lire `docs/service-notes.md`.

Le port est `8080`. Cette version ne publie pas d’API HTTP : les services restent une couche interne appelée par le code applicatif/test.
