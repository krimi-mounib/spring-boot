package tn.esprit.mohamedmounibkrimicce11.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.mohamedmounibkrimicce11.service.IPrixSimulationService;
import tn.esprit.mohamedmounibkrimicce11.web.dto.SimulationPrixRequest;
import tn.esprit.mohamedmounibkrimicce11.web.dto.SimulationPrixResponse;

@RestController
@RequestMapping("/simulations-prix")
@RequiredArgsConstructor
@Tag(name = "Simulations de prix", description = "Estimation sans réservation, paiement ni notification")
public class PrixSimulationController {

    private final IPrixSimulationService prixSimulationService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Simuler le prix d'une réservation", description = "Calcule un montant estimé sans écrire en base.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estimation calculée ; aucune donnée n'est enregistrée"),
            @ApiResponse(responseCode = "400", description = "Paramètres ou corps JSON invalides"),
            @ApiResponse(responseCode = "404", description = "Réservation introuvable")
    })
    public SimulationPrixResponse simuler(
            @Parameter(description = "Paramètres de simulation") @Valid @RequestBody SimulationPrixRequest request) {
        return prixSimulationService.simuler(request);
    }
}
