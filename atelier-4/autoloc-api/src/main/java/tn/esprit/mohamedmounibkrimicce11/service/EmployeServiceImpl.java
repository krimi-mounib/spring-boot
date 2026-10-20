package tn.esprit.mohamedmounibkrimicce11.service;

import java.util.List;
import tn.esprit.mohamedmounibkrimicce11.domain.Employe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mohamedmounibkrimicce11.repository.IEmployeRepository;
import tn.esprit.mohamedmounibkrimicce11.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {
    private final IEmployeRepository repository;
    @Override public Employe create(Employe entity) {
        if (entity == null) throw new IllegalArgumentException("Employe obligatoire");
        if (entity.getIdEmploye() != null) throw new IllegalArgumentException("Un nouvel employe ne doit pas avoir d'identifiant");
        validate(entity); return repository.save(entity);
    }
    @Override public Employe findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employe", id)); }
    @Override public List<Employe> findAll() { return repository.findAll(); }
    @Override public Employe update(Long id, Employe entity) {
        if (entity == null) throw new IllegalArgumentException("Employe obligatoire");
        Employe existing = findById(id); validate(entity);
        existing.setNom(entity.getNom()); existing.setPrenom(entity.getPrenom()); existing.setRole(entity.getRole()); existing.setAgence(entity.getAgence());
        return repository.save(existing);
    }
    @Override public void deleteById(Long id) { if (!repository.existsById(id)) throw new ResourceNotFoundException("Employe", id); repository.deleteById(id); }
    private void validate(Employe entity) { requireText(entity.getNom(), "nom"); requireText(entity.getPrenom(), "prenom"); if (entity.getRole() == null) throw new IllegalArgumentException("Le rôle est obligatoire"); }
    private void requireText(String value, String field) { if (value == null || value.isBlank()) throw new IllegalArgumentException("Le champ " + field + " est obligatoire"); }
}
