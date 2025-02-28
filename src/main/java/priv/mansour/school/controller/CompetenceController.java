package priv.mansour.school.controller;

import static priv.mansour.school.utils.Constants.COMPETENCE;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import priv.mansour.school.entity.Competence;
import priv.mansour.school.logger.GlobalLogger;
import priv.mansour.school.services.ICompetenceService;

@RestController
@RequestMapping("/competences")
public class CompetenceController {

    private final ICompetenceService competenceService;

    @Autowired
    public CompetenceController(ICompetenceService competenceService) {
        this.competenceService = competenceService;
    }

    @PostMapping
    public ResponseEntity<Competence> addCompetence(@Valid @RequestBody Competence competence) {
        GlobalLogger.infoCreate(COMPETENCE, "Creating new competence: " + competence.getLibelle());
        Competence createdCompetence = competenceService.add(competence);
        GlobalLogger.infoSuccess("Created", COMPETENCE, "Competence ID: " + createdCompetence.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCompetence);
    }

    @GetMapping
    public ResponseEntity<List<Competence>> getAllCompetences() {
        GlobalLogger.infoReadAll(COMPETENCE);
        List<Competence> competences = competenceService.getAll();
        GlobalLogger.infoSuccess("Fetched all", COMPETENCE, "Total competences: " + competences.size());
        return ResponseEntity.ok(competences);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Competence> getCompetenceById(@PathVariable @NotBlank String id) {
        GlobalLogger.infoRead(COMPETENCE, "Fetching competence by ID: " + id);
        Competence competence = competenceService.getById(id);
        GlobalLogger.infoSuccess("Fetched by ID", COMPETENCE, "Competence ID: " + competence.getId());
        return ResponseEntity.ok(competence);
    }

    @GetMapping("/by-libelle/{libelle}")
    public ResponseEntity<Competence> getCompetenceByLibelle(@PathVariable @NotBlank String libelle) {
        GlobalLogger.infoRead(COMPETENCE, "Fetching competence by Libelle: " + libelle);
        Competence competence = competenceService.getCompetenceByLibelle(libelle);
        GlobalLogger.infoSuccess("Fetched by Libelle", COMPETENCE, "Competence Libelle: " + competence.getLibelle());
        return ResponseEntity.ok(competence);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Competence> updateCompetence(@PathVariable @NotBlank String id,
                                                       @Valid @RequestBody Competence updatedCompetence) {
        GlobalLogger.infoUpdate(COMPETENCE,  id, updatedCompetence);
        Competence updated = competenceService.update(id, updatedCompetence);
        GlobalLogger.infoSuccess("Updated", COMPETENCE, "Competence ID: " + updated.getId());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompetenceById(@PathVariable @NotBlank String id) {
        GlobalLogger.infoDelete(COMPETENCE, "Deleting competence ID: " + id);
        competenceService.deleteById(id);
        GlobalLogger.infoSuccess("Deleted", COMPETENCE, "Competence ID: " + id);
        return ResponseEntity.noContent().build();
    }
}
