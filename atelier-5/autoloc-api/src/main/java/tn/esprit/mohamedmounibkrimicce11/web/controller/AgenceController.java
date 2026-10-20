package tn.esprit.mohamedmounibkrimicce11.web.controller;

import tn.esprit.mohamedmounibkrimicce11.domain.Agence;
import tn.esprit.mohamedmounibkrimicce11.service.IAgenceService;
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
@RequestMapping("/agences")
@RequiredArgsConstructor
@Tag(name = "Agences", description = "Gestion des agences AutoLoc")
public class AgenceController {
    private final IAgenceService service;
    @GetMapping @Operation(summary = "Lister les agences") @ApiResponse(responseCode = "200", description = "Liste retournée")
    public List<Agence> findAll() { return service.findAll(); }
    @GetMapping("/{id}") @Operation(summary = "Consulter une agence")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Ressource trouvée"), @ApiResponse(responseCode = "404", description = "Ressource introuvable")})
    public Agence findById(@Parameter(description = "Identifiant de la ressource", example = "1") @PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Créer une agence")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Ressource créée"), @ApiResponse(responseCode = "400", description = "Corps invalide ou identifiant fourni"), @ApiResponse(responseCode = "409", description = "Conflit d’intégrité des données")})
    public Agence create(@RequestBody Agence entity) { return service.create(entity); }
    @PutMapping("/{id}") @Operation(summary = "Modifier une agence")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Ressource modifiée"), @ApiResponse(responseCode = "400", description = "Valeur invalide"), @ApiResponse(responseCode = "404", description = "Ressource introuvable"), @ApiResponse(responseCode = "409", description = "Conflit d’intégrité des données")})
    public Agence update(@Parameter(description = "Identifiant de la ressource", example = "1") @PathVariable Long id, @RequestBody Agence entity) { return service.update(id, entity); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Supprimer une agence")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Ressource supprimée"), @ApiResponse(responseCode = "404", description = "Ressource introuvable"), @ApiResponse(responseCode = "409", description = "Ressource encore référencée")})
    public void deleteById(@Parameter(description = "Identifiant de la ressource", example = "1") @PathVariable Long id) { service.deleteById(id); }
}
