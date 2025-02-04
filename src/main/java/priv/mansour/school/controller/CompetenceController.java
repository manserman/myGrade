package priv.mansour.school.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import priv.mansour.school.services.CompetenceService;

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
		return ResponseEntity.ok(competenceService.addCompetence(competence));

	}

	@GetMapping("/getAll")
	public ResponseEntity<List<Competence>> getAllCompetences() {
		return ResponseEntity.ok(competenceService.getAllCompetences());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Competence> getCompetenceById(@PathVariable int id) {
		return ResponseEntity.ok(competenceService.getCompetenceById(id));
	}

	@GetMapping("/by-libelle")
	public ResponseEntity<Competence> getCompetenceByLibelle(@RequestParam String libelle) {

		return ResponseEntity.ok(competenceService.getCompetenceByLibelle(libelle));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Competence> updateCompetence(@PathVariable int id,
			@RequestBody Competence updatedCompetence) {

		return ResponseEntity.ok(competenceService.updateCompetence(id, updatedCompetence));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCompetenceById(@PathVariable int id) {
		competenceService.deleteCompetenceById(id);
		return ResponseEntity.noContent().build();
	}
}
