package tn.esprit.mohamedmounibkrimicce11.web.controller;

import tn.esprit.mohamedmounibkrimicce11.domain.Equipement;
import tn.esprit.mohamedmounibkrimicce11.service.IEquipementService;
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
@RequestMapping("/equipements")
@RequiredArgsConstructor
@Tag(name = "Équipements", description = "Gestion des équipements AutoLoc")
public class EquipementController {
    private final IEquipementService service;
    @GetMapping @Operation(summary = "Lister les équipements") @ApiResponse(responseCode = "200", description = "Liste retournée")
    public List<Equipement> findAll() { return service.findAll(); }
    @GetMapping("/{id}") @Operation(summary = "Consulter un équipement")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Ressource trouvée"), @ApiResponse(responseCode = "404", description = "Introuvable")})
    public Equipement findById(@Parameter(description = "Identifiant", example = "1") @PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Créer un équipement")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Ressource créée"), @ApiResponse(responseCode = "400", description = "Données invalides"), @ApiResponse(responseCode = "409", description = "Conflit d’intégrité")})
    public Equipement create(@RequestBody Equipement entity) { return service.create(entity); }
    @PutMapping("/{id}") @Operation(summary = "Modifier un équipement")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Ressource modifiée"), @ApiResponse(responseCode = "400", description = "Données invalides"), @ApiResponse(responseCode = "404", description = "Introuvable"), @ApiResponse(responseCode = "409", description = "Conflit d’intégrité")})
    public Equipement update(@Parameter(description = "Identifiant", example = "1") @PathVariable Long id, @RequestBody Equipement entity) { return service.update(id, entity); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Supprimer un équipement")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Supprimé"), @ApiResponse(responseCode = "404", description = "Introuvable"), @ApiResponse(responseCode = "409", description = "Toujours référencé")})
    public void deleteById(@Parameter(description = "Identifiant", example = "1") @PathVariable Long id) { service.deleteById(id); }
}
