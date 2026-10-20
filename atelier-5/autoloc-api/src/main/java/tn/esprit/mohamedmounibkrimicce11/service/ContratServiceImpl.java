package tn.esprit.mohamedmounibkrimicce11.service;

import java.util.List;
import tn.esprit.mohamedmounibkrimicce11.domain.Contrat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mohamedmounibkrimicce11.repository.IContratRepository;
import tn.esprit.mohamedmounibkrimicce11.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {
    private final IContratRepository repository;
    @Override public Contrat create(Contrat entity) {
        if (entity == null) throw new IllegalArgumentException("Contrat obligatoire");
        if (entity.getIdContrat() != null) throw new IllegalArgumentException("Un nouvel contrat ne doit pas avoir d'identifiant");
        validate(entity);
        if (entity.getPaiements() != null) entity.getPaiements().forEach(paiement -> paiement.setContrat(entity));
        return repository.save(entity);
    }
    @Override public Contrat findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contrat", id)); }
    @Override public List<Contrat> findAll() { return repository.findAll(); }
    @Override public Contrat update(Long id, Contrat entity) {
        if (entity == null) throw new IllegalArgumentException("Contrat obligatoire");
        Contrat existing = findById(id); validate(entity);
        existing.setDateSignature(entity.getDateSignature()); existing.setMontantTotal(entity.getMontantTotal()); existing.setValide(entity.isValide());
        if (entity.getReservation() != null) existing.setReservation(entity.getReservation());
        return repository.save(existing);
    }
    @Override public void deleteById(Long id) { if (!repository.existsById(id)) throw new ResourceNotFoundException("Contrat", id); repository.deleteById(id); }
    private void validate(Contrat entity) {
        if (entity.getDateSignature() == null) throw new IllegalArgumentException("La date de signature est obligatoire");
        if (entity.getMontantTotal() == null || entity.getMontantTotal().signum() < 0) throw new IllegalArgumentException("Le montant total doit être positif ou nul");
    }
}
