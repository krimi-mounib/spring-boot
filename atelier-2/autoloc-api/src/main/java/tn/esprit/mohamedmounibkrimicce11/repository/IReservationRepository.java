package tn.esprit.mohamedmounibkrimicce11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.mohamedmounibkrimicce11.domain.Reservation;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {
}
