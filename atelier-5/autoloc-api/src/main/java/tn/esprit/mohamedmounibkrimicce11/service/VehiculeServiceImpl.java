package tn.esprit.mohamedmounibkrimicce11.service;

import java.util.List;
import tn.esprit.mohamedmounibkrimicce11.domain.Vehicule;
import java.math.BigDecimal;
import tn.esprit.mohamedmounibkrimicce11.domain.CategorieVehicule;
import tn.esprit.mohamedmounibkrimicce11.domain.StatutVehicule;
import tn.esprit.mohamedmounibkrimicce11.domain.Agence;
import tn.esprit.mohamedmounibkrimicce11.domain.Equipement;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mohamedmounibkrimicce11.repository.IVehiculeRepository;
import tn.esprit.mohamedmounibkrimicce11.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {
    private final IVehiculeRepository repository;

    @Override
    public Vehicule create(Vehicule entity) {
        if (entity == null) throw new IllegalArgumentException("Vehicule obligatoire");
        if (entity.getIdVehicule() != null) {
            throw new IllegalArgumentException("Un nouvel vehicule ne doit pas avoir d'identifiant");
        }
        validate(entity);
        return repository.save(entity);
    }

    @Override
    public Vehicule findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    public List<Vehicule> findAll() {
        return repository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule entity) {
        if (entity == null) throw new IllegalArgumentException("Vehicule obligatoire");
        Vehicule existing = findById(id);
        validate(entity);
        existing.setImmatriculation(entity.getImmatriculation());
        existing.setMarque(entity.getMarque());
        existing.setModele(entity.getModele());
        existing.setCategorie(entity.getCategorie());
        existing.setTarifJournalier(entity.getTarifJournalier());
        existing.setStatut(entity.getStatut());
        existing.setAgence(entity.getAgence());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Vehicule", id);
        repository.deleteById(id);
    }

    private void validate(Vehicule entity) {
        requireText(entity.getImmatriculation(), "immatriculation");
        requireText(entity.getMarque(), "marque");
        requireText(entity.getModele(), "modele");
        if (entity.getTarifJournalier() == null || entity.getTarifJournalier().signum() < 0) throw new IllegalArgumentException("Le tarif journalier doit être positif ou nul");
    }

    private void requireText(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("Le champ " + field + " est obligatoire");
    }
}
