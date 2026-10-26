package tn.esprit.mohamedmounibkrimicce11.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;
import tn.esprit.mohamedmounibkrimicce11.domain.ModePaiement;
import tn.esprit.mohamedmounibkrimicce11.domain.Reservation;
import tn.esprit.mohamedmounibkrimicce11.web.dto.SimulationPrixResponse;

/**
 * Atelier 6 — Mission 1 (SRP) : contient les règles de calcul du prix et aucun effet secondaire.
 * Ne crée ni contrat ni paiement, ne modifie pas la réservation et n'envoie aucune notification.
 */
@Service
public class CalculateurPrixService implements ICalculateurPrixService {

    private static final BigDecimal ZERO = new BigDecimal("0.00");
    private static final BigDecimal PROMO_BIENVENUE = new BigDecimal("20.00");
    private static final BigDecimal FRAIS_CARTE = new BigDecimal("0.01");

    @Override
    public SimulationPrixResponse simuler(Reservation reservation, String typeClient,
                                          String codePromo, ModePaiement modePaiement) {
        if (reservation == null) {
            throw new IllegalArgumentException("La réservation est obligatoire");
        }
        if (reservation.getDateDebut() == null || reservation.getDateFin() == null
                || reservation.getDateFin().isBefore(reservation.getDateDebut())) {
            throw new IllegalArgumentException("La période de location est invalide");
        }
        if (reservation.getVehicule() == null || reservation.getVehicule().getTarifJournalier() == null) {
            throw new IllegalArgumentException("La réservation doit référencer un véhicule avec un tarif journalier");
        }
        if (typeClient == null || !(typeClient.equals("PARTICULIER")
                || typeClient.equals("ENTREPRISE") || typeClient.equals("VIP"))) {
            throw new IllegalArgumentException("Type client inconnu : " + typeClient);
        }
        if (modePaiement == null) {
            throw new IllegalArgumentException("Le mode de paiement est obligatoire");
        }

        long jours = ChronoUnit.DAYS.between(reservation.getDateDebut(), reservation.getDateFin()) + 1;
        BigDecimal tarif = money(reservation.getVehicule().getTarifJournalier());
        BigDecimal prixBase = money(tarif.multiply(BigDecimal.valueOf(jours)));

        BigDecimal tauxRemise = switch (typeClient) {
            case "PARTICULIER" -> jours >= 7 ? new BigDecimal("0.10") : ZERO;
            case "ENTREPRISE" -> new BigDecimal("0.15");
            case "VIP" -> new BigDecimal("0.20");
            default -> ZERO; // validé ci-dessus
        };
        BigDecimal remiseClient = money(prixBase.multiply(tauxRemise));
        BigDecimal apresRemise = money(prixBase.subtract(remiseClient));

        // Règle reprise de LocationManager fourni : BIENVENUE retranche 20 après la remise client.
        BigDecimal remisePromo = "BIENVENUE".equals(codePromo) ? PROMO_BIENVENUE : ZERO;
        BigDecimal avantFrais = money(apresRemise.subtract(remisePromo));
        BigDecimal fraisPaiement = modePaiement == ModePaiement.CARTE
                ? money(avantFrais.multiply(FRAIS_CARTE)) : ZERO;
        BigDecimal montantFinal = money(avantFrais.add(fraisPaiement));

        return new SimulationPrixResponse(
                reservation.getIdReservation(), jours, typeClient, modePaiement.name(),
                tarif, prixBase, remiseClient, remisePromo, fraisPaiement, montantFinal, false);
    }

    private BigDecimal money(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }
}
