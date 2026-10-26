package tn.esprit.mohamedmounibkrimicce11.web.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimulationPrixResponse {
    private final Long reservationId;
    private final long nombreJours;
    private final String typeClient;
    private final String modePaiement;
    private final BigDecimal tarifJournalier;
    private final BigDecimal prixBase;
    private final BigDecimal remiseClient;
    private final BigDecimal remisePromo;
    private final BigDecimal fraisPaiement;
    private final BigDecimal montantFinal;
    private final boolean persiste;
}
