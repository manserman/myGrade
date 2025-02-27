package priv.mansour.school.controller;

import static priv.mansour.school.utils.Constants.COMPETENCE;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/new")
    public ResponseEntity<Competence> addCompetence(@Valid @RequestBody Competence competence) {
        GlobalLogger.infoCreate(COMPETENCE, competence);
        Competence createdCompetence = competenceService.add(competence);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCompetence);
    }

    @GetMapping()
    public ResponseEntity<List<Competence>> getAllCompetences() {
        GlobalLogger.infoReadAll(COMPETENCE);
        List<Competence> competences = competenceService.getAll();
        return ResponseEntity.ok(competences);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Competence> getCompetenceById(@PathVariable String id) {
        GlobalLogger.infoRead(COMPETENCE, id);
        Competence competence = competenceService.getById(id);
        return ResponseEntity.ok(competence);
    }

    @GetMapping("/by-libelle/{libelle}")
    public ResponseEntity<Competence> getCompetenceByLibelle(@PathVariable String libelle) {
        GlobalLogger.infoAction("Fetching by libelle", COMPETENCE, libelle);
        Competence competence = competenceService.getCompetenceByLibelle(libelle);
        return ResponseEntity.ok(competence);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Competence> updateCompetence(@PathVariable String id,
                                                       @Valid @RequestBody Competence updatedCompetence) {
        GlobalLogger.infoUpdate(COMPETENCE, id, updatedCompetence);
        Competence updated = competenceService.update(id, updatedCompetence);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompetenceById(@PathVariable String id) {
        GlobalLogger.infoDelete(COMPETENCE, id);
        competenceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
