package tn.esprit.mohamedmounibkrimicce11.web.controller;

import tn.esprit.mohamedmounibkrimicce11.domain.Paiement;
import tn.esprit.mohamedmounibkrimicce11.service.IPaiementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
@Tag(name = "Paiements", description = "Consultation des paiements (lecture seule)")
public class PaiementController {
    private final IPaiementService paiementService;
    @GetMapping @Operation(summary = "Lister les paiements") @ApiResponse(responseCode = "200", description = "Liste retournée")
    public List<Paiement> findAll() { return paiementService.findAll(); }
    @GetMapping("/{id}") @Operation(summary = "Consulter un paiement")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Paiement trouvé"), @ApiResponse(responseCode = "404", description = "Paiement introuvable")})
    public Paiement findById(@Parameter(description = "Identifiant du paiement", example = "1") @PathVariable Long id) { return paiementService.findById(id); }
}
