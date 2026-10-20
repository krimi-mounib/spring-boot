package tn.esprit.mohamedmounibkrimicce11.service;

import java.util.List;
import tn.esprit.mohamedmounibkrimicce11.domain.Maintenance;

public interface IMaintenanceService {
    Maintenance create(Maintenance entity);
    Maintenance findById(Long id);
    List<Maintenance> findAll();
    Maintenance update(Long id, Maintenance entity);
    void deleteById(Long id);
}
