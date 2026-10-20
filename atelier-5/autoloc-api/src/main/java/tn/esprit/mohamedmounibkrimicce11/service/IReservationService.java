package tn.esprit.mohamedmounibkrimicce11.service;

import java.util.List;
import tn.esprit.mohamedmounibkrimicce11.domain.Reservation;

public interface IReservationService {
    Reservation create(Reservation entity);
    Reservation findById(Long id);
    List<Reservation> findAll();
    Reservation update(Long id, Reservation entity);
    void deleteById(Long id);
}
