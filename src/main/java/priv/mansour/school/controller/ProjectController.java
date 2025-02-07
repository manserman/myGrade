package priv.mansour.school.controller;

import static priv.mansour.school.utils.Constants.PROJECT;

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
import priv.mansour.school.entity.Project;
import priv.mansour.school.logger.GlobalLogger;
import priv.mansour.school.services.ProjectService;

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
		GlobalLogger.infoCreate(PROJECT, project);
		return ResponseEntity.ok(projectService.addProject(project));
	}

	@GetMapping("/all")
	public ResponseEntity<List<Project>> getAllProjects() {
		GlobalLogger.infoReadAll(PROJECT);
		return ResponseEntity.ok(projectService.getAllProjects());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Project> getProjectById(@PathVariable String id) {
		GlobalLogger.infoRead(PROJECT, id);
		return ResponseEntity.ok(projectService.getProjectById(id));
	}

	@GetMapping("/by-libelle")
	public ResponseEntity<Project> getProjectByLibelle(@RequestParam String libelle) {
		GlobalLogger.infoRead(PROJECT, "Libelle: " + libelle);
		return ResponseEntity.ok(projectService.getProjectByLibelle(libelle));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Project> updateProject(@PathVariable String id, @RequestBody Project updatedProject) {
		GlobalLogger.infoUpdate(PROJECT, id, updatedProject);
		return ResponseEntity.ok(projectService.updateProject(id, updatedProject));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProjectById(@PathVariable String id) {
		GlobalLogger.infoDelete(PROJECT, id);
		projectService.deleteProjectById(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{id}/competences")
	public ResponseEntity<Void> addCompetenceToProject(@PathVariable String id, @RequestBody Competence competence) {
		GlobalLogger.infoAction("Adding Competence", PROJECT, "Project ID: " + id + " Competence: " + competence);
		projectService.addCompetenceToProject(id, competence);
		return ResponseEntity.ok().build();
	}
}
