# Atelier 2 — Associations JPA : Cascade et Fetch

## Association guidée Contrat ↔ Paiement

- `Paiement.contrat` est le côté propriétaire (`@ManyToOne(fetch = LAZY)`) : la clé étrangère est dans la table `paiement`.
- `Contrat.paiements` est le côté inverse (`mappedBy = "contrat"`) : il ne crée ni nouvelle colonne dans `contrat`, ni table de jointure.
- `cascade = CascadeType.ALL` propage les opérations de persistance du contrat à ses paiements.
- `orphanRemoval = true` supprime de la base un paiement retiré de la collection.
- Le fetch retenu est `LAZY` pour éviter de charger des paiements qui ne sont pas consultés.

## Autres associations du modèle

| Association | Côté propriétaire | Côté inverse | Cascade / fetch |
|---|---|---|---|
| Agence — Véhicule | `Vehicule.agence` (`ManyToOne`) | `Agence.vehicules` (`mappedBy="agence"`) | Pas de cascade REMOVE; `LAZY` |
| Agence — Employé | `Employe.agence` | `Agence.employes` | Pas de cascade REMOVE; `LAZY` |
| Client — Réservation | `Reservation.client` | `Client.reservations` | Pas de cascade REMOVE; `LAZY` |
| Véhicule — Réservation | `Reservation.vehicule` | `Vehicule.reservations` | Pas de cascade REMOVE; `LAZY` |
| Véhicule — Maintenance | `Maintenance.vehicule` | `Vehicule.maintenances` | Pas de cascade REMOVE; `LAZY` |
| Véhicule — Équipement | `Vehicule.equipements` | `Equipement.vehicules` (`mappedBy="equipements"`) | Many-to-many; `LAZY` |
| Réservation — Contrat | `Contrat.reservation` | `Reservation.contrat` (`mappedBy="reservation"`) | `LAZY`; pas de cascade REMOVE |

## Vérifications dans MySQL

1. Démarrer avec `spring.jpa.hibernate.ddl-auto=update` et examiner les logs SQL.
2. Vérifier la clé étrangère créée dans `paiement` pour l'attribut `contrat` (le nom dépend de la stratégie de nommage Hibernate).
3. Vérifier que `mappedBy` correspond exactement au nom du champ propriétaire.
4. Vérifier qu'aucune table de jointure n'est créée pour `Contrat.paiements`.

## Limites du modèle fourni

Le modèle ne comporte pas de champ `quantite` dans `Equipement`, de champ `cout` dans `Maintenance`, ni de kilométrage dans `Vehicule`. Ces attributs ne sont pas inventés dans cette version.
