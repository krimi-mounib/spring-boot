package tn.esprit.mohamedmounibkrimicce11.service;

import java.util.List;
import tn.esprit.mohamedmounibkrimicce11.domain.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mohamedmounibkrimicce11.repository.IReservationRepository;
import tn.esprit.mohamedmounibkrimicce11.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {
    private final IReservationRepository repository;
    @Override public Reservation create(Reservation entity) {
        if (entity == null) throw new IllegalArgumentException("Réservation obligatoire");
        if (entity.getIdReservation() != null) throw new IllegalArgumentException("Une nouvelle réservation ne doit pas avoir d'identifiant");
        validate(entity); return repository.save(entity);
    }
    @Override public Reservation findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation", id)); }
    @Override public List<Reservation> findAll() { return repository.findAll(); }
    @Override public Reservation update(Long id, Reservation entity) {
        if (entity == null) throw new IllegalArgumentException("Réservation obligatoire");
        Reservation existing = findById(id); validate(entity);
        existing.setDateDebut(entity.getDateDebut()); existing.setDateFin(entity.getDateFin()); existing.setStatut(entity.getStatut()); existing.setVehicule(entity.getVehicule()); existing.setClient(entity.getClient());
        return repository.save(existing);
    }
    @Override public void deleteById(Long id) { if (!repository.existsById(id)) throw new ResourceNotFoundException("Reservation", id); repository.deleteById(id); }
    private void validate(Reservation entity) {
        if (entity.getDateDebut() == null || entity.getDateFin() == null) throw new IllegalArgumentException("Les dates de réservation sont obligatoires");
        if (entity.getDateFin().isBefore(entity.getDateDebut())) throw new IllegalArgumentException("La date de fin doit être postérieure ou égale à la date de début");
        if (entity.getStatut() == null) throw new IllegalArgumentException("Le statut est obligatoire");
        if (entity.getClient() == null || entity.getClient().getIdClient() == null) throw new IllegalArgumentException("Un client existant est obligatoire");
        if (entity.getVehicule() == null || entity.getVehicule().getIdVehicule() == null) throw new IllegalArgumentException("Un véhicule existant est obligatoire");
    }
}
