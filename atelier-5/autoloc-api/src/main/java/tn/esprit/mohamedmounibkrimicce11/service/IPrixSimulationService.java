package tn.esprit.mohamedmounibkrimicce11.service;

import tn.esprit.mohamedmounibkrimicce11.web.dto.SimulationPrixRequest;
import tn.esprit.mohamedmounibkrimicce11.web.dto.SimulationPrixResponse;

public interface IPrixSimulationService {
    SimulationPrixResponse simuler(SimulationPrixRequest request);
}
