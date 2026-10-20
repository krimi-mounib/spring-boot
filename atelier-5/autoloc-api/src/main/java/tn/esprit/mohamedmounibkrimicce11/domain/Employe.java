package tn.esprit.mohamedmounibkrimicce11.domain;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Entity @Table(name = "employe") @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Employe {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long idEmploye;
    @Column(nullable = false, length = 100) private String nom;
    @Column(nullable = false, length = 100) private String prenom;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private RoleEmploye role;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) @ManyToOne(fetch = FetchType.LAZY) private Agence agence;
}
