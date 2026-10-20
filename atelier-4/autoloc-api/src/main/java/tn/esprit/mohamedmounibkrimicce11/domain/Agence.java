package tn.esprit.mohamedmounibkrimicce11.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity @Table(name = "agence") @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Agence {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence;
    @Column(nullable = false, length = 100) private String nom;
    @Column(nullable = false, length = 100) private String ville;
    @Column(nullable = false, length = 255) private String adresse;
    @Column(nullable = false, length = 20) private String telephone;
    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY) private Set<Vehicule> vehicules = new HashSet<>();
    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY) private Set<Employe> employes = new HashSet<>();
}
