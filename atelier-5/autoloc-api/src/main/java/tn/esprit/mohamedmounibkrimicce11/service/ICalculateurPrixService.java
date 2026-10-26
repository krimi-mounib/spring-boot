package tn.esprit.mohamedmounibkrimicce11.service;

import tn.esprit.mohamedmounibkrimicce11.domain.Reservation;
import tn.esprit.mohamedmounibkrimicce11.domain.ModePaiement;
import tn.esprit.mohamedmounibkrimicce11.web.dto.SimulationPrixResponse;

public interface ICalculateurPrixService {
    SimulationPrixResponse simuler(Reservation reservation, String typeClient,
                                   String codePromo, ModePaiement modePaiement);
}
