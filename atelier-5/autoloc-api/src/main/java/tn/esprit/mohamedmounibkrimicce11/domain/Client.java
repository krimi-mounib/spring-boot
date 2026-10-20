package tn.esprit.mohamedmounibkrimicce11.domain;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity @Table(name = "client") @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long idClient;
    @Column(nullable = false, length = 100) private String nom;
    @Column(nullable = false, length = 100) private String prenom;
    @Column(nullable = false, unique = true, length = 150) private String email;
    @Column(nullable = false, length = 20) private String telephone;
    @Column(nullable = false, unique = true, length = 30) private String numPermis;
    @Column(nullable = false) private LocalDate dateInscription;
    @JsonIgnore @OneToMany(mappedBy = "client", fetch = FetchType.LAZY) private Set<Reservation> reservations = new HashSet<>();
}
