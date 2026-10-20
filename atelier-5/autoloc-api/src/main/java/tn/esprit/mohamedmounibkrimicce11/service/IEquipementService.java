package tn.esprit.mohamedmounibkrimicce11.service;

import java.util.List;
import tn.esprit.mohamedmounibkrimicce11.domain.Equipement;

public interface IEquipementService {
    Equipement create(Equipement entity);
    Equipement findById(Long id);
    List<Equipement> findAll();
    Equipement update(Long id, Equipement entity);
    void deleteById(Long id);
}
