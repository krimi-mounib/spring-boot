package tn.esprit.mohamedmounibkrimicce11.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity @Table(name = "vehicule") @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Vehicule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long idVehicule;
    @Column(nullable = false, unique = true, length = 30) private String immatriculation;
    @Column(nullable = false, length = 80) private String marque;
    @Column(nullable = false, length = 80) private String modele;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 30) private CategorieVehicule categorie;
    @Column(nullable = false, precision = 10, scale = 2) private BigDecimal tarifJournalier;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 30) private StatutVehicule statut;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) private Agence agence;
    @ManyToMany(fetch = FetchType.LAZY) private Set<Equipement> equipements = new HashSet<>();
    @OneToMany(mappedBy = "vehicule", fetch = FetchType.LAZY) private Set<Reservation> reservations = new HashSet<>();
    @OneToMany(mappedBy = "vehicule", fetch = FetchType.LAZY) private Set<Maintenance> maintenances = new HashSet<>();
}
