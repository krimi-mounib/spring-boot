package tn.esprit.mohamedmounibkrimicce11.service;

import java.util.List;
import tn.esprit.mohamedmounibkrimicce11.domain.Equipement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mohamedmounibkrimicce11.repository.IEquipementRepository;
import tn.esprit.mohamedmounibkrimicce11.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {
    private final IEquipementRepository repository;
    @Override public Equipement create(Equipement entity) {
        if (entity == null) throw new IllegalArgumentException("Equipement obligatoire");
        if (entity.getIdEquipement() != null) throw new IllegalArgumentException("Un nouvel equipement ne doit pas avoir d'identifiant");
        validate(entity); return repository.save(entity);
    }
    @Override public Equipement findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Equipement", id)); }
    @Override public List<Equipement> findAll() { return repository.findAll(); }
    @Override public Equipement update(Long id, Equipement entity) {
        if (entity == null) throw new IllegalArgumentException("Equipement obligatoire");
        Equipement existing = findById(id); validate(entity); existing.setLibelle(entity.getLibelle()); return repository.save(existing);
    }
    @Override public void deleteById(Long id) { if (!repository.existsById(id)) throw new ResourceNotFoundException("Equipement", id); repository.deleteById(id); }
    private void validate(Equipement entity) { if (entity.getLibelle() == null || entity.getLibelle().isBlank()) throw new IllegalArgumentException("Le libellé est obligatoire"); }
}
