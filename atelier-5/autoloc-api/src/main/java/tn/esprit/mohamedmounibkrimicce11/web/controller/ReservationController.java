package tn.esprit.mohamedmounibkrimicce11.web.controller;

import tn.esprit.mohamedmounibkrimicce11.domain.Reservation;
import tn.esprit.mohamedmounibkrimicce11.service.IReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Tag(name = "Réservations", description = "Gestion des réservations AutoLoc")
public class ReservationController {
    private final IReservationService service;
    @GetMapping @Operation(summary = "Lister les réservations") @ApiResponse(responseCode = "200", description = "Liste retournée")
    public List<Reservation> findAll() { return service.findAll(); }
    @GetMapping("/{id}") @Operation(summary = "Consulter une réservation")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Ressource trouvée"), @ApiResponse(responseCode = "404", description = "Introuvable")})
    public Reservation findById(@Parameter(description = "Identifiant", example = "1") @PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Créer une réservation")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Ressource créée"), @ApiResponse(responseCode = "400", description = "Données invalides"), @ApiResponse(responseCode = "409", description = "Conflit d’intégrité")})
    public Reservation create(@RequestBody Reservation entity) { return service.create(entity); }
    @PutMapping("/{id}") @Operation(summary = "Modifier une réservation")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Ressource modifiée"), @ApiResponse(responseCode = "400", description = "Données invalides"), @ApiResponse(responseCode = "404", description = "Introuvable"), @ApiResponse(responseCode = "409", description = "Conflit d’intégrité")})
    public Reservation update(@Parameter(description = "Identifiant", example = "1") @PathVariable Long id, @RequestBody Reservation entity) { return service.update(id, entity); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Supprimer une réservation")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Supprimée"), @ApiResponse(responseCode = "404", description = "Introuvable"), @ApiResponse(responseCode = "409", description = "Toujours référencée")})
    public void deleteById(@Parameter(description = "Identifiant", example = "1") @PathVariable Long id) { service.deleteById(id); }
}
