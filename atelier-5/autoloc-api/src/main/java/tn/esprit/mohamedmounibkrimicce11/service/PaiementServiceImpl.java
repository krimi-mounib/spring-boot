package tn.esprit.mohamedmounibkrimicce11.service;

import java.util.List;
import tn.esprit.mohamedmounibkrimicce11.domain.Paiement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mohamedmounibkrimicce11.repository.IPaiementRepository;
import tn.esprit.mohamedmounibkrimicce11.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {
    private final IPaiementRepository repository;
    @Override public Paiement findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Paiement", id)); }
    @Override public List<Paiement> findAll() { return repository.findAll(); }
}
