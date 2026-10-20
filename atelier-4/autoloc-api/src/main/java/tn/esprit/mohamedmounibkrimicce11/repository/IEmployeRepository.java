package tn.esprit.mohamedmounibkrimicce11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.mohamedmounibkrimicce11.domain.Employe;

public interface IEmployeRepository extends JpaRepository<Employe, Long> {
}
