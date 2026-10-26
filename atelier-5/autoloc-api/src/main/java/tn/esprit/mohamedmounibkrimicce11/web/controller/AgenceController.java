package tn.esprit.mohamedmounibkrimicce11.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mohamedmounibkrimicce11.domain.Agence;
import tn.esprit.mohamedmounibkrimicce11.service.IAgenceService;
import java.util.List;

@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
@Tag(name = "Agences", description = "Gestion des agences AutoLoc")
public class AgenceController {
    private final IAgenceService agenceService;

    @GetMapping
    @Operation(summary = "Lister les agences")
    @ApiResponse(responseCode = "200", description = "Liste retournée")
    public List<Agence> findAll() { return agenceService.findAll(); }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter une agence")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Agence trouvée"), @ApiResponse(responseCode = "404", description = "Agence introuvable")})
    public Agence findById(@PathVariable Long id) { return agenceService.findById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer une agence")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Agence créée"), @ApiResponse(responseCode = "400", description = "Données invalides")})
    public Agence create(@RequestBody Agence agence) { return agenceService.create(agence); }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une agence")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Agence modifiée"), @ApiResponse(responseCode = "400", description = "Données invalides"), @ApiResponse(responseCode = "404", description = "Agence introuvable")})
    public Agence update(@PathVariable Long id, @RequestBody Agence agence) { return agenceService.update(id, agence); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer une agence")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Agence supprimée"), @ApiResponse(responseCode = "404", description = "Agence introuvable")})
    public void deleteById(@PathVariable Long id) { agenceService.deleteById(id); }
}
