package tn.esprit.mohamedmounibkrimicce11.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mohamedmounibkrimicce11.domain.Reservation;
import tn.esprit.mohamedmounibkrimicce11.web.dto.SimulationPrixRequest;
import tn.esprit.mohamedmounibkrimicce11.web.dto.SimulationPrixResponse;

@Service
@RequiredArgsConstructor
public class PrixSimulationService implements IPrixSimulationService {

    private final IReservationService reservationService;
    private final ICalculateurPrixService calculateurPrixService;

    @Override
    public SimulationPrixResponse simuler(SimulationPrixRequest request) {
        if (request == null || request.getReservationId() == null) {
            throw new IllegalArgumentException("reservationId est obligatoire");
        }
        Reservation reservation = reservationService.findById(request.getReservationId());
        return calculateurPrixService.simuler(reservation, request.getTypeClient(),
                request.getCodePromo(), request.getModePaiement());
    }
}
