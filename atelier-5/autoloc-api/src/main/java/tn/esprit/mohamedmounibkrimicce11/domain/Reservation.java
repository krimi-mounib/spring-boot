package tn.esprit.mohamedmounibkrimicce11.domain;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import java.time.LocalDate;

@Entity @Table(name = "reservation") @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long idReservation;
    @Column(nullable = false) private LocalDate dateDebut;
    @Column(nullable = false) private LocalDate dateFin;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private StatutReservation statut;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) @ManyToOne(fetch = FetchType.LAZY, optional = false) private Vehicule vehicule;
    @JsonIgnore @OneToOne(mappedBy = "reservation", fetch = FetchType.LAZY) private Contrat contrat;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) @ManyToOne(fetch = FetchType.LAZY, optional = false) private Client client;
}
