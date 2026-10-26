# AutoLoc — Ateliers 4, 5 et 6 (Mission 1)

Ce dossier contient le projet Maven complet, les contrôleurs REST de l'Atelier 5 et l'ajout de l'Atelier 6 : **Mission 1 — Le simulateur de prix**.

## 1. Ouvrir et démarrer

### Mode conseillé pour tester rapidement (H2 en mémoire)

Aucune installation MySQL n'est nécessaire en mode `demo`. Dans un terminal ouvert dans ce dossier :

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=demo
```

Dans IntelliJ, ajoutez `demo` dans **Run > Edit Configurations > Active profiles** puis lancez l'application. Le profil crée une base H2 en mémoire et insère automatiquement une agence, un client, un véhicule et une réservation d'exemple. Pour retrouver l'identifiant exact de réservation, faites `GET /api/reservations`. Les données H2 sont effacées à l'arrêt de l'application.

### Mode MySQL (base habituelle)

1. Décompresser l'archive.
2. Ouvrir le dossier `mohamedmounibkrimiCCE111/mohamedmounibkrimiCCE11` dans IntelliJ IDEA comme projet Maven.
3. Vérifier Java 17 ou plus récent. Le `pom.xml` utilise Spring Boot 4 et SpringDoc 3.
4. Démarrer MySQL et créer/configurer la base `autoloc_db`. Le projet utilise `jdbc:mysql://localhost:3306/autoloc_db?createDatabaseIfNotExist=true`.
5. Par défaut, les identifiants sont `root` et mot de passe vide. Sinon définir les variables d'environnement `DB_USERNAME` et `DB_PASSWORD` avant le lancement.
6. Dans IntelliJ, ouvrir la fenêtre Maven et lancer `Lifecycle > package`, puis exécuter la classe `MohamedmounibkrimiCce11Application`. Dans un terminal, si Maven est installé : `mvn spring-boot:run`. Sous Windows, `mvnw.cmd spring-boot:run` peut être utilisé après téléchargement réussi de Maven par le wrapper.
7. L'application écoute sur le port **8081** (défini dans `application.properties`).

> Le Maven Wrapper du ZIP doit télécharger Maven depuis Maven Central lors du premier lancement. Dans l'environnement qui a servi à préparer cette archive, ce téléchargement était bloqué par l'accès réseau ; la compilation ne peut donc pas être déclarée « testée » ici. Lancez `mvn clean package` dans votre environnement IntelliJ avec accès réseau.

## 2. Où accéder à l'API

- Swagger UI : http://localhost:8081/api/swagger-ui.html
- Spécification OpenAPI JSON : http://localhost:8081/api/v3/api-docs
- Exemple liste agences : http://localhost:8081/api/agences
- Exemple liste réservations : http://localhost:8081/api/reservations

Le préfixe `/api` vient de `server.servlet.context-path=/api`. Les contrôleurs n'incluent donc pas `/api` dans `@RequestMapping`.

## 3. Atelier 5 — API REST

CRUD complet pour les entités suivantes :

| Entité | GET liste | GET détail | POST | PUT | DELETE |
|---|---|---|---|---|---|
| Agence | `/api/agences` | `/api/agences/{id}` | Oui | Oui | Oui |
| Véhicule | `/api/vehicules` | `/api/vehicules/{id}` | Oui | Oui | Oui |
| Client | `/api/clients` | `/api/clients/{id}` | Oui | Oui | Oui |
| Employé | `/api/employes` | `/api/employes/{id}` | Oui | Oui | Oui |
| Équipement | `/api/equipements` | `/api/equipements/{id}` | Oui | Oui | Oui |
| Réservation | `/api/reservations` | `/api/reservations/{id}` | Oui | Oui | Oui |
| Contrat | `/api/contrats` | `/api/contrats/{id}` | Oui | Oui | Oui |
| Maintenance | `/api/maintenances` | `/api/maintenances/{id}` | Oui | Oui | Oui |
| Paiement | `/api/paiements` | `/api/paiements/{id}` | Non (lecture seule) | Non | Non |

Codes habituels : `200` lecture/modification, `201` création, `204` suppression, `400` JSON ou règle métier invalide, `404` ressource introuvable, `405` méthode non exposée, `409` conflit d'intégrité en base.

### Exemple Postman — simulation de prix (Atelier 6)

La simulation a besoin d'une réservation existante. En profil `demo`, une réservation est créée automatiquement ; remplacez `1` par l'identifiant réel renvoyé par `GET /api/reservations`.

- Verbe : `POST`
- URL : `http://localhost:8081/api/simulations-prix`
- Headers : `Content-Type: application/json`
- Body > raw > JSON :

```json
{
  "reservationId": 1,
  "typeClient": "VIP",
  "codePromo": "BIENVENUE",
  "modePaiement": "CARTE"
}
```

Réponse attendue : `200 OK`, avec `nombreJours`, `prixBase`, `remiseClient`, `remisePromo`, `fraisPaiement` et `montantFinal`. Avec les données de démonstration (7 jours à 120.00, VIP, BIENVENUE, CARTE), le montant final attendu est **658.52**. `persiste` vaut `false`. La simulation ne crée pas de contrat/paiement et n'envoie pas de notification.

`typeClient` accepte `PARTICULIER`, `ENTREPRISE`, `VIP`; le modèle de l'Atelier 5 ne stocke pas actuellement ce champ dans `Client`, donc il est fourni dans la demande de simulation. `codePromo` peut être absent/null ou `BIENVENUE`. Modes : `CARTE`, `VIREMENT`, `ESPECES`.

### Test de cas d'erreur

- `GET http://localhost:8081/api/reservations/999999` → `404`.
- `POST http://localhost:8081/api/paiements` avec `Content-Type: application/json` → `405` (paiements en lecture seule).
- `POST http://localhost:8081/api/simulations-prix` avec `reservationId: 999999` → `404`.
- Même URL avec `typeClient: "ROBOT"` → `400`.

## 4. Atelier 6 — Mission choisie

**Mission 1 — Le simulateur de prix.** Principe appliqué : SRP (Single Responsibility Principle, responsabilité unique). Le code de calcul est séparé dans `CalculateurPrixService`, et la simulation se limite à charger la réservation puis demander le calcul. Le calculateur est sans effet secondaire : il n'appelle aucun repository pour enregistrer, ne crée aucun paiement/contrat et n'envoie aucun message.

Le `LocationManager` fourni dans le texte de l'Atelier 6 ne se trouve pas dans le ZIP du projet. Pour que la mission soit démontrable dans ce projet réel, une route REST `POST /api/simulations-prix` est ajoutée. Quand votre équipe intégrera plus tard le flux de confirmation fourni dans l'énoncé, ce flux devra appeler le même `ICalculateurPrixService` pour garantir que les deux parcours appliquent les mêmes règles.

Règles reprises de l'énoncé : tarif journalier × nombre de jours inclusifs ; particulier −10 % à partir de 7 jours ; entreprise −15 % ; VIP −20 % ; coupon `BIENVENUE` −20 ; paiement par carte +1 %. Le montant est calculé avec `BigDecimal` arrondi à deux décimales.

## 5. Collection Postman

Importer `postman/AutoLoc-Ateliers-5-6.postman_collection.json` dans Postman. L'environnement `postman/AutoLoc-local.postman_environment.json` est fourni. La collection contient les requêtes de démarrage, création de données, cascade contrat/paiements, simulation prix, et cas d'erreur. Lancez les requêtes de création dans l'ordre indiqué car certaines utilisent les identifiants capturés par les scripts Postman.

## 6. Captures d'écran à faire pour le rendu

1. IntelliJ : les fichiers `CalculateurPrixService`, `PrixSimulationService` et `PrixSimulationController`.
2. Swagger UI ouvert sur `Simulations de prix`.
3. Postman : simulation réussie (`200`) avec une réservation existante.
4. Postman : réservation inexistante (`404`) et type client invalide (`400`).
5. Postman : `POST /api/paiements` qui renvoie `405`.

## 7. Précisions importantes

- Les IDs JSON suivent les vrais getters du modèle : `idAgence`, `idClient`, `idVehicule`, `idReservation`, `idContrat`, etc.
- Les entités sont exposées directement comme prévu par l'Atelier 5 ; les DTO restent l'amélioration de conception recommandée.
- `Reservation.contrat` est ignoré dans JSON afin d'éviter une boucle infinie avec `Contrat.reservation`. Pour créer un contrat lié à une réservation, utiliser le champ `reservation` dans le corps du contrat.
- En profil `demo`, une base H2 et les entités d'exemple sont créées au démarrage ; en profil par défaut, MySQL et des données cohérentes sont nécessaires. Aucun résultat HTTP « live » n'est revendiqué tant que l'application n'a pas été lancée sur votre machine.
