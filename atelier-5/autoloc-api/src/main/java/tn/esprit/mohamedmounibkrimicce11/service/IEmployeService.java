package tn.esprit.mohamedmounibkrimicce11.service;

import java.util.List;
import tn.esprit.mohamedmounibkrimicce11.domain.Employe;

public interface IEmployeService {
    Employe create(Employe entity);
    Employe findById(Long id);
    List<Employe> findAll();
    Employe update(Long id, Employe entity);
    void deleteById(Long id);
}
