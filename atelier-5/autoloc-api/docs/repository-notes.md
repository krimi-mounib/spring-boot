# Atelier 3 - Notes sur la couche Repository

## Choix d'interface

| Interface | Etend | Justification |
|---|---|---|
| IAgenceRepository | JpaRepository<Agence, Long> | CRUD complet, findAll renvoie une List, tri, pagination, flush. |
| IEmployeRepository | JpaRepository<Employe, Long> | Idem. |
| IVehiculeRepository | JpaRepository<Vehicule, Long> | Idem. Corrige : CrudRepository<Vehicule, long> (type primitif interdit) |
| IEquipementRepository | JpaRepository<Equipement, Long> | Idem. |
| IClientRepository | JpaRepository<Client, Long> | Idem. |
| IReservationRepository | JpaRepository<Reservation, Long> | Idem. |
| IContratRepository | JpaRepository<Contrat, Long> | CRUD complet, findAll renvoie une List, saveAndFlush disponible. |
| IPaiementRepository | JpaRepository<Paiement, Long> | Pour lire les paiements ; creation/suppression via le Contrat. |
| IMaintenanceRepository | JpaRepository<Maintenance, Long> | Idem. |

JpaRepository est retenue car elle cumule CRUD, listes (ListCrudRepository), tri et pagination,
et ajoute flush/saveAndFlush. Attention : deleteAllInBatch contourne cascade et orphanRemoval.

## Anomalies SonarQube for IDE corrigees

| Anomalie | Regle / explication | Correction apportee |
|---|---|---|
| IVehiculeRepository<Vehicule, long> | Type generique primitif impossible : l'ID doit etre `Long`. | Remplace par JpaRepository<Vehicule, Long>. |
| Import inutile `java.util.List` (Agence), `java.util.Set` (Employe) | java:S1128 - imports inutilises. | Supprimes. |
| Imports `jakarta.persistence.*` | java:S2208 - pas d'import avec joker. | Imports explicites dans les 9 entites. |
| Attributs `Agence`, `Vehicule`, `Client`... (majuscule, meme nom que le type) | java:S116 - nom de champ non conforme (camelCase). | Renommes (agence, vehicules, reservations...), mappedBy mis a jour. |
| Attributs en visibilite package | Encapsulation (java:S1104). | Passes en `private`. |
| `;` isole dans Maintenance | java:S1116 - instruction vide. | Supprime. |
| `@ToString` sur une entite (Maintenance) | Risque de boucle/chargement de relations. | Supprime. |
| `cascade = ALL` sur @ManyToOne Maintenance -> Vehicule | Supprimer une maintenance supprimerait le vehicule. | Cascade retiree, fetch passe en LAZY. |

## Autre ajustement
- `spring.jpa.hibernate.ddl-auto` : `create-drop` -> `update` (prerequis de l'atelier : ne pas perdre les donnees).
