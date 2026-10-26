package tn.esprit.mohamedmounibkrimicce11.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.mohamedmounibkrimicce11.domain.ModePaiement;

@Getter
@Setter
@NoArgsConstructor
public class SimulationPrixRequest {

    @NotNull(message = "reservationId est obligatoire")
    private Long reservationId;

    // Le modèle Atelier 5 ne stocke pas ce type sur Client : il est donc fourni pour la simulation.
    @NotBlank(message = "typeClient est obligatoire")
    @Pattern(regexp = "PARTICULIER|ENTREPRISE|VIP",
            message = "typeClient doit être PARTICULIER, ENTREPRISE ou VIP")
    private String typeClient;

    private String codePromo;

    @NotNull(message = "modePaiement est obligatoire")
    private ModePaiement modePaiement;
}
