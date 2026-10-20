package tn.esprit.mohamedmounibkrimicce11.service;

import java.util.List;
import tn.esprit.mohamedmounibkrimicce11.domain.Contrat;

public interface IContratService {
    Contrat create(Contrat entity);
    Contrat findById(Long id);
    List<Contrat> findAll();
    Contrat update(Long id, Contrat entity);
    void deleteById(Long id);
}
