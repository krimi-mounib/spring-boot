package tn.esprit.mohamedmounibkrimicce11.service;

import java.util.List;
import tn.esprit.mohamedmounibkrimicce11.domain.Agence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mohamedmounibkrimicce11.repository.IAgenceRepository;
import tn.esprit.mohamedmounibkrimicce11.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {
    private final IAgenceRepository repository;

    @Override
    public Agence create(Agence entity) {
        if (entity == null) throw new IllegalArgumentException("Agence obligatoire");
        if (entity.getIdAgence() != null) throw new IllegalArgumentException("Un nouvel agence ne doit pas avoir d'identifiant");
        validate(entity);
        return repository.save(entity);
    }
    @Override public Agence findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Agence", id)); }
    @Override public List<Agence> findAll() { return repository.findAll(); }
    @Override public Agence update(Long id, Agence entity) {
        if (entity == null) throw new IllegalArgumentException("Agence obligatoire");
        Agence existing = findById(id); validate(entity);
        existing.setNom(entity.getNom()); existing.setVille(entity.getVille()); existing.setAdresse(entity.getAdresse()); existing.setTelephone(entity.getTelephone());
        return repository.save(existing);
    }
    @Override public void deleteById(Long id) { if (!repository.existsById(id)) throw new ResourceNotFoundException("Agence", id); repository.deleteById(id); }
    private void validate(Agence entity) { requireText(entity.getNom(), "nom"); requireText(entity.getVille(), "ville"); requireText(entity.getAdresse(), "adresse"); requireText(entity.getTelephone(), "telephone"); }
    private void requireText(String value, String field) { if (value == null || value.isBlank()) throw new IllegalArgumentException("Le champ " + field + " est obligatoire"); }
}
