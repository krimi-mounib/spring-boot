package tn.esprit.mohamedmounibkrimicce11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.mohamedmounibkrimicce11.domain.Maintenance;

public interface IMaintenanceRepository extends JpaRepository<Maintenance, Long> {
}
