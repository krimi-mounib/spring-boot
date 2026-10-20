package tn.esprit.mohamedmounibkrimicce11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.mohamedmounibkrimicce11.domain.Paiement;

public interface IPaiementRepository extends JpaRepository<Paiement, Long> {
}
