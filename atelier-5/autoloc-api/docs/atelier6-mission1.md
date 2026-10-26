# Atelier 6 — Mission 1 : Le simulateur de prix

## Demande

Permettre de calculer une estimation tarifaire sans réserver, sans payer et sans envoyer de notifications. Le code du projet d'Atelier 5 ne contenait pas le `LocationManager` de l'énoncé Atelier 6 ; la mission est donc intégrée sous la forme d'une route de simulation réellement utilisable avec l'entité `Reservation` du ZIP.

## Principe : SRP

Le *Single Responsibility Principle* signifie qu'une classe doit avoir une responsabilité principale, donc une raison principale de changer. Le calcul du prix ne doit pas être mêlé à la création de contrats/paiements ni aux notifications.

## Avant / après

- Avant : dans le code fourni par l'énoncé Atelier 6, les blocs disponibilité, tarif, remises, paiement, contrat et notification se trouvent dans `LocationManager.confirmerReservation`. Une simulation ne peut pas appeler cette méthode sans déclencher trop d'effets, et copier le calcul risquerait de diverger.
- Après dans ce projet : `CalculateurPrixService` porte les règles tarifaires ; `PrixSimulationService` charge la réservation puis appelle le calculateur ; `PrixSimulationController` expose un endpoint. Le flux de simulation n'a aucune opération d'écriture.

## Schéma

```text
POST /api/simulations-prix
          |
          v
PrixSimulationController (web)
          |
          v
IPrixSimulationService / PrixSimulationService
          |                                   |                          -> ICalculateurPrixService
          v                                  |
IReservationService.findById                v
          |                       CalculateurPrixService
          v                                  |
     Reservation --------------------------> montant estimé
```

## Règles tarifaires reprises de l'énoncé

- Prix de base = tarif journalier × (nombre de jours inclusifs).
- `PARTICULIER` : −10 % si location d'au moins 7 jours.
- `ENTREPRISE` : −15 %.
- `VIP` : −20 %.
- Code `BIENVENUE` : −20 après la remise du client.
- `CARTE` : +1 % après les remises ; `VIREMENT` et `ESPECES` : aucun frais dans la règle fournie.
- Calcul monétaire avec `BigDecimal` à deux décimales.

Le modèle `Client` actuel ne contient pas de champ `type`. Pour ne pas altérer le schéma Atelier 5, `typeClient` est passé explicitement à la simulation. Dans une version ultérieure, la même valeur pourra provenir du profil client.

## Endpoint et exemple

`POST http://localhost:8081/api/simulations-prix`

```json
{
  "reservationId": 1,
  "typeClient": "VIP",
  "codePromo": "BIENVENUE",
  "modePaiement": "CARTE"
}
```

Le corps de réponse expose le nombre de jours, le tarif, le prix de base, les montants de remise client et promo, les frais carte et le montant final. `persiste: false` rappelle qu'aucune donnée n'a été enregistrée.

## Scénarios de test

1. Réservation existante, 2 jours, tarif 100, VIP, pas de promo, virement : base 200.00, remise client 40.00, total 160.00.
2. Même réservation, mode carte : frais 1.60, total 161.60.
3. Réservation de 7 jours, tarif 100, particulier, virement : base 700.00, remise 70.00, total 630.00.
4. Avec `BIENVENUE`, retrancher 20.00 après la remise client.
5. Identifiant inexistant : 404.
6. Type client invalide / mode manquant / JSON invalide : 400.
7. Après plusieurs simulations, vérifier que GET `/api/contrats` et GET `/api/paiements` n'ont pas changé : aucune création ne doit avoir eu lieu.

## Limite connue

Cette intégration fournit un calculateur partagé, mais la classe `LocationManager` de l'énoncé n'existe pas dans le projet fourni. Quand l'implémentation de confirmation sera intégrée, elle devra appeler `ICalculateurPrixService` avec les mêmes paramètres au lieu de recopier les règles. La simulation actuelle, elle, compile avec les classes de domaine réelles du ZIP.

## Prompt de découverte utilisé dans ce rendu

| Prompt (tel quel) | Ce qui a été retenu | Correction / écart |
|---|---|---|
| « i want you to do me one of these missions only one and do it perfectly and do waht is demanded in the image as well here is my work so far complete it if it's missingfrom tp5 » | Inspecter le projet, terminer les livrables de l'Atelier 5, choisir une mission de l'Atelier 6 et expliquer les tests. | Ne pas supposer que le `LocationManager` de l'énoncé est déjà dans le ZIP ; adapter la mission à ses vrais packages et getters, et noter la limite clairement. |
