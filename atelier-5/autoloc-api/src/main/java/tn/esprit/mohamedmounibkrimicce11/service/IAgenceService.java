package tn.esprit.mohamedmounibkrimicce11.service;

import java.util.List;
import tn.esprit.mohamedmounibkrimicce11.domain.Agence;

public interface IAgenceService {
    Agence create(Agence entity);
    Agence findById(Long id);
    List<Agence> findAll();
    Agence update(Long id, Agence entity);
    void deleteById(Long id);
}
