package priv.mansour.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import priv.mansour.school.entity.Competence;
import priv.mansour.school.services.CompetenceService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/competences")
public class CompetenceController {

	private final CompetenceService competenceService;

	@Autowired
	public CompetenceController(CompetenceService competenceService) {
		this.competenceService = competenceService;
	}

	@PostMapping("/new")
	public ResponseEntity<Competence> addCompetence(@RequestBody Competence competence) {
		try {
			return ResponseEntity.ok(competenceService.addCompetence(competence));
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<Competence>> getAllCompetences() {
		return ResponseEntity.ok(competenceService.getAllCompetences());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Competence> getCompetenceById(@PathVariable int id) {
		Optional<Competence> competence = competenceService.getCompetenceById(id);
		return competence.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/by-libelle")
	public ResponseEntity<Competence> getCompetenceByLibelle(@RequestParam String libelle) {
		Competence competence = competenceService.getCompetenceByLibelle(libelle);
		if (competence != null) {
			return ResponseEntity.ok(competence);
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Competence> updateCompetence(@PathVariable int id,
			@RequestBody Competence updatedCompetence) {
		try {
			return ResponseEntity.ok(competenceService.updateCompetence(id, updatedCompetence));
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCompetenceById(@PathVariable int id) {
		competenceService.deleteCompetenceById(id);
		return ResponseEntity.noContent().build();
	}
}
