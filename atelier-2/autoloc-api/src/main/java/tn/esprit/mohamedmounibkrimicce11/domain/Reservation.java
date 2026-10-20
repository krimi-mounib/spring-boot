package tn.esprit.mohamedmounibkrimicce11.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity @Table(name = "reservation") @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long idReservation;
    @Column(nullable = false) private LocalDate dateDebut;
    @Column(nullable = false) private LocalDate dateFin;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private StatutReservation statut;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) private Vehicule vehicule;
    // Coté inverse : Contrat porte la cle etrangere vers Reservation.
    @OneToOne(mappedBy = "reservation", fetch = FetchType.LAZY) private Contrat contrat;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) private Client client;
}
