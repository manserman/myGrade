package priv.mansour.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import priv.mansour.school.entity.Project;
import priv.mansour.school.entity.Competence;
import priv.mansour.school.services.ProjectService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
public class ProjectController {

	private final ProjectService projectService;

	@Autowired
	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@PostMapping("/new")
	public ResponseEntity<Project> addProject(@RequestBody Project project) {
		return ResponseEntity.ok(projectService.addProject(project));
	}

	@GetMapping("/all")
	public ResponseEntity<List<Project>> getAllProjects() {
		return ResponseEntity.ok(projectService.getAllProjects());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Project> getProjectById(@PathVariable int id) {
		Optional<Project> project = projectService.getProjectById(id);
		return project.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/by-libelle")
	public ResponseEntity<Project> getProjectByLibelle(@RequestParam String libelle) {
		Project project = projectService.getProjectByLibelle(libelle);
		if (project != null) {
			return ResponseEntity.ok(project);
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Project> updateProject(@PathVariable int id, @RequestBody Project updatedProject) {
		try {
			return ResponseEntity.ok(projectService.updateProject(id, updatedProject));
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProjectById(@PathVariable int id) {
		projectService.deleteProjectById(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{id}/competences")
	public ResponseEntity<Void> addCompetenceToProject(@PathVariable int id, @RequestBody Competence competence) {
		try {
			projectService.addCompetenceToProject(id, competence);
			return ResponseEntity.ok().build();
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
