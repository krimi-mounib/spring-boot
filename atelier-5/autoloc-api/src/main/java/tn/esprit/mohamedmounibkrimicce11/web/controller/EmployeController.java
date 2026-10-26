package tn.esprit.mohamedmounibkrimicce11.web.controller;

import tn.esprit.mohamedmounibkrimicce11.domain.Employe;
import tn.esprit.mohamedmounibkrimicce11.service.IEmployeService;
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
@RequestMapping("/employes")
@RequiredArgsConstructor
@Tag(name = "Employés", description = "Gestion des employés AutoLoc")
public class EmployeController {
    private final IEmployeService service;

    @GetMapping
    @Operation(summary = "Lister les employés")
    @ApiResponse(responseCode = "200", description = "Liste retournée")
    public List<Employe> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un employé")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ressource trouvée"),
        @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    public Employe findById(@Parameter(description = "Identifiant de la ressource", example = "1") @PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un employé")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Ressource créée"),
        @ApiResponse(responseCode = "400", description = "Corps invalide ou identifiant fourni")
    })
    public Employe create(@RequestBody Employe entity) { return service.create(entity); }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un employé")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ressource modifiée"),
        @ApiResponse(responseCode = "400", description = "Valeur invalide"),
        @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    public Employe update(@Parameter(description = "Identifiant de la ressource", example = "1") @PathVariable Long id,
                           @RequestBody Employe entity) { return service.update(id, entity); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un employé")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Ressource supprimée"),
        @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    public void deleteById(@Parameter(description = "Identifiant de la ressource", example = "1") @PathVariable Long id) {
        service.deleteById(id);
    }
}
