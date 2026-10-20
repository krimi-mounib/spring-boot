package tn.esprit.mohamedmounibkrimicce11.service;

import java.util.List;
import tn.esprit.mohamedmounibkrimicce11.domain.Vehicule;

public interface IVehiculeService {
    Vehicule create(Vehicule entity);
    Vehicule findById(Long id);
    List<Vehicule> findAll();
    Vehicule update(Long id, Vehicule entity);
    void deleteById(Long id);
}
