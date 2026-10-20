# Atelier 4 — Notes sur la couche Service

## A. Modes d’injection comparés

| Critère | Constructeur (`@RequiredArgsConstructor`) | Attribut (`@Autowired`) |
|---|---|---|
| Champ `final` possible | Oui | Non |
| Dépendance visible | Oui, dans le constructeur généré | Non, cachée dans la classe |
| Instanciation hors Spring | Possible si on fournit la dépendance | Risque de champ `null` |
| Qualité / Sonar | Pratique recommandée | Généralement signalée (ex. S6813 selon la version) |

## B. Services

| Service | Dépendance injectée | Mode | Justification |
|---|---|---|---|
| `AgenceServiceImpl` | `IAgenceRepository` | Constructeur | Valide les champs obligatoires et centralise le CRUD. |
| `VehiculeServiceImpl` | `IVehiculeRepository` | Constructeur | Valide les champs texte et le tarif journalier. |
| `EmployeServiceImpl` | `IEmployeRepository` | Constructeur | Valide nom, prénom et rôle. |
| `EquipementServiceImpl` | `IEquipementRepository` | Constructeur | Valide le libellé. Le modèle fourni n’a pas de quantité. |
| `ClientServiceImpl` | `IClientRepository` | Constructeur | Valide les champs d’identification. |
| `ReservationServiceImpl` | `IReservationRepository` | Constructeur | Vérifie l’ordre des dates. |
| `ContratServiceImpl` | `IContratRepository` | Constructeur | Refuse un montant total négatif et préserve les associations lors de l’update. |
| `PaiementServiceImpl` | `IPaiementRepository` | Constructeur | Lecture seule conformément à la consigne. |
| `MaintenanceServiceImpl` | `IMaintenanceRepository` | Constructeur | Vérifie les dates. Le modèle fourni ne comporte pas de coût. |

## C. Messages d’erreur Spring

1. **Bean `IContratService` introuvable** : `ContratServiceImpl` peut manquer de `@Service`, ou être situé hors du package scanné. Vérifier l’annotation et le package sous la classe principale.
2. **Deux beans candidats** : placer `@Primary` sur l’implémentation choisie par défaut, ou `@Qualifier("nomDuBean")` sur le paramètre du constructeur pour choisir explicitement.
3. **Dépendance circulaire** : deux services se requièrent mutuellement au constructeur, donc aucun ne peut être construit en premier. Extraire la responsabilité commune dans un troisième service ou inverser la dépendance via une abstraction adaptée.

## D. Points de qualité appliqués

- Remplacement de l’ancien service véhicule incomplet par `IVehiculeService` / `VehiculeServiceImpl`, avec injection constructeur et champ `final`.
- Ajout de `ResourceNotFoundException` plutôt que d’une exception générique pour les identifiants absents.
- Refus des identifiants fournis à la création, validation métier et mise à jour par chargement puis copie des champs modifiables.
- Les collections inverses ne sont pas recopiées pendant les mises à jour, afin d’éviter d’effacer les relations par inadvertance.

> Les règles SonarQube exactes dépendent de la version du plugin installé. Exécuter l’analyse dans IntelliJ et compléter cette section avec les règles effectivement affichées.

## E. Questions de compréhension

1. Le code ne contient pas de `new ContratServiceImpl`. Spring crée le bean au démarrage à partir de `@Service`, puis résout son repository au constructeur.
2. Le contrôleur dépend de `IContratService` pour rester couplé à une abstraction et pouvoir remplacer l’implémentation sans changer le contrôleur.
3. Un singleton est partagé entre requêtes et threads. Un champ mutable comme `contratCourant` pourrait être écrasé par deux requêtes simultanées.
4. L’update charge l’entité persistée puis copie les champs modifiables pour préserver l’identifiant et les associations/collections non concernées.
5. `@Component` déclare un bean via scan ; `@Bean` déclare explicitement le résultat d’une méthode `@Configuration`. `@Primary` choisit le candidat par défaut ; `@Qualifier` nomme le candidat attendu. Paiement est en lecture seule car il est géré dans le cycle de vie du Contrat selon la consigne.
