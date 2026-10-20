package tn.esprit.mohamedmounibkrimicce11.domain;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import java.time.LocalDate;

@Entity @Table(name = "maintenance") @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Maintenance {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long idMaintenance;
    @Column(nullable = false) private LocalDate dateDebut;
    private LocalDate dateFin;
    @Column(length = 500) private String description;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) @ManyToOne(fetch = FetchType.LAZY, optional = false) private Vehicule vehicule;
}
