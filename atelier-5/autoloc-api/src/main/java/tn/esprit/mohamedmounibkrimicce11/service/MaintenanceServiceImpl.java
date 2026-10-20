package tn.esprit.mohamedmounibkrimicce11.service;

import java.util.List;
import tn.esprit.mohamedmounibkrimicce11.domain.Maintenance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mohamedmounibkrimicce11.repository.IMaintenanceRepository;
import tn.esprit.mohamedmounibkrimicce11.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {
    private final IMaintenanceRepository repository;
    @Override public Maintenance create(Maintenance entity) {
        if (entity == null) throw new IllegalArgumentException("Maintenance obligatoire");
        if (entity.getIdMaintenance() != null) throw new IllegalArgumentException("Une nouvelle maintenance ne doit pas avoir d'identifiant");
        validate(entity); return repository.save(entity);
    }
    @Override public Maintenance findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Maintenance", id)); }
    @Override public List<Maintenance> findAll() { return repository.findAll(); }
    @Override public Maintenance update(Long id, Maintenance entity) {
        if (entity == null) throw new IllegalArgumentException("Maintenance obligatoire");
        Maintenance existing = findById(id); validate(entity);
        existing.setDateDebut(entity.getDateDebut()); existing.setDateFin(entity.getDateFin()); existing.setDescription(entity.getDescription()); existing.setVehicule(entity.getVehicule());
        return repository.save(existing);
    }
    @Override public void deleteById(Long id) { if (!repository.existsById(id)) throw new ResourceNotFoundException("Maintenance", id); repository.deleteById(id); }
    private void validate(Maintenance entity) {
        if (entity.getDateDebut() == null) throw new IllegalArgumentException("La date de début est obligatoire");
        if (entity.getDateFin() != null && entity.getDateFin().isBefore(entity.getDateDebut())) throw new IllegalArgumentException("La date de fin ne peut pas précéder la date de début");
        if (entity.getVehicule() == null || entity.getVehicule().getIdVehicule() == null) throw new IllegalArgumentException("Un véhicule existant est obligatoire");
    }
}
