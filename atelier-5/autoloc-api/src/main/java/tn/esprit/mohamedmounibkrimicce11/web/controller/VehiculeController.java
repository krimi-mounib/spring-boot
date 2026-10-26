package tn.esprit.mohamedmounibkrimicce11.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mohamedmounibkrimicce11.domain.Vehicule;
import tn.esprit.mohamedmounibkrimicce11.service.IVehiculeService;
import java.util.List;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
@Tag(name = "Véhicules", description = "Gestion des véhicules AutoLoc")
public class VehiculeController {
    private final IVehiculeService vehiculeService;

    @GetMapping
    @Operation(summary = "Lister les véhicules")
    @ApiResponse(responseCode = "200", description = "Liste retournée")
    public List<Vehicule> findAll() { return vehiculeService.findAll(); }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un véhicule")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Véhicule trouvé"), @ApiResponse(responseCode = "404", description = "Véhicule introuvable")})
    public Vehicule findById(@PathVariable Long id) { return vehiculeService.findById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un véhicule")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Véhicule créé"), @ApiResponse(responseCode = "400", description = "Données invalides")})
    public Vehicule create(@RequestBody Vehicule vehicule) { return vehiculeService.create(vehicule); }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un véhicule")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Véhicule modifié"), @ApiResponse(responseCode = "400", description = "Données invalides"), @ApiResponse(responseCode = "404", description = "Véhicule introuvable")})
    public Vehicule update(@PathVariable Long id, @RequestBody Vehicule vehicule) { return vehiculeService.update(id, vehicule); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un véhicule")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Véhicule supprimé"), @ApiResponse(responseCode = "404", description = "Véhicule introuvable")})
    public void deleteById(@PathVariable Long id) { vehiculeService.deleteById(id); }
}
