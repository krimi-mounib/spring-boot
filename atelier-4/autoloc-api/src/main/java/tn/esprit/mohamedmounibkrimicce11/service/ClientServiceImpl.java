package tn.esprit.mohamedmounibkrimicce11.service;

import java.util.List;
import tn.esprit.mohamedmounibkrimicce11.domain.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mohamedmounibkrimicce11.repository.IClientRepository;
import tn.esprit.mohamedmounibkrimicce11.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {
    private final IClientRepository repository;
    @Override public Client create(Client entity) {
        if (entity == null) throw new IllegalArgumentException("Client obligatoire");
        if (entity.getIdClient() != null) throw new IllegalArgumentException("Un nouvel client ne doit pas avoir d'identifiant");
        validate(entity); return repository.save(entity);
    }
    @Override public Client findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client", id)); }
    @Override public List<Client> findAll() { return repository.findAll(); }
    @Override public Client update(Long id, Client entity) {
        if (entity == null) throw new IllegalArgumentException("Client obligatoire");
        Client existing = findById(id); validate(entity);
        existing.setNom(entity.getNom()); existing.setPrenom(entity.getPrenom()); existing.setEmail(entity.getEmail()); existing.setTelephone(entity.getTelephone()); existing.setNumPermis(entity.getNumPermis()); existing.setDateInscription(entity.getDateInscription());
        return repository.save(existing);
    }
    @Override public void deleteById(Long id) { if (!repository.existsById(id)) throw new ResourceNotFoundException("Client", id); repository.deleteById(id); }
    private void validate(Client entity) {
        requireText(entity.getNom(), "nom"); requireText(entity.getPrenom(), "prenom"); requireText(entity.getEmail(), "email"); requireText(entity.getTelephone(), "telephone"); requireText(entity.getNumPermis(), "numPermis");
        if (entity.getDateInscription() == null) throw new IllegalArgumentException("La date d’inscription est obligatoire");
    }
    private void requireText(String value, String field) { if (value == null || value.isBlank()) throw new IllegalArgumentException("Le champ " + field + " est obligatoire"); }
}
