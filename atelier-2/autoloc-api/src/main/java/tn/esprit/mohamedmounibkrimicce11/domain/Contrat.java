package tn.esprit.mohamedmounibkrimicce11.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "contrat") @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Contrat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long idContrat;
    @Column(nullable = false) private LocalDate dateSignature;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal montantTotal;
    @Column(nullable = false) private boolean valide;
    // Cote proprietaire de Contrat <-> Reservation.
    @OneToOne(fetch = FetchType.LAZY) private Reservation reservation;
    // Composition : les paiements suivent le cycle de vie du contrat.
    @OneToMany(mappedBy = "contrat", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Paiement> paiements = new ArrayList<>();

    public void addPaiement(Paiement paiement) {
        if (paiement == null) return;
        paiements.add(paiement);
        paiement.setContrat(this);
    }
    public void removePaiement(Paiement paiement) {
        if (paiements.remove(paiement)) paiement.setContrat(null);
    }
}
