package priv.mansour.school.controller;

import static priv.mansour.school.utils.Constants.PROJECT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
		GlobalLogger.infoCreate(PROJECT, "Creating new project: " + project.getLibelle());
		Project createdProject = projectService.addProject(project);
		GlobalLogger.infoSuccess("Created", PROJECT, "Project ID: " + createdProject.getId());
		return ResponseEntity.ok(createdProject);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Project>> getAllProjects() {
		GlobalLogger.infoReadAll(PROJECT);
		return ResponseEntity.ok(projectService.getAllProjects());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Project> getProjectById(@PathVariable String id) {
		GlobalLogger.infoRead(PROJECT, "Fetching project by ID: " + id);
		return ResponseEntity.ok(projectService.getProjectById(id));
	}

	@GetMapping("/by-libelle")
	public ResponseEntity<Project> getProjectByLibelle(@RequestParam String libelle) {
		GlobalLogger.infoRead(PROJECT, "Fetching project by Libelle: " + libelle);
		return ResponseEntity.ok(projectService.getProjectByLibelle(libelle));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Project> updateProject(@PathVariable String id, @RequestBody Project updatedProject) {
		GlobalLogger.infoUpdate(PROJECT, "Updating project ID: ", id);
		Project updated = projectService.updateProject(id, updatedProject);
		GlobalLogger.infoSuccess("Updated", PROJECT, "Project ID: " + updated.getId());
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProjectById(@PathVariable String id) {
		GlobalLogger.infoDelete(PROJECT, "Deleting project ID: " + id);
		projectService.deleteProjectById(id);
		GlobalLogger.infoSuccess("Deleted", PROJECT, "Project ID: " + id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{id}/competences")
	public ResponseEntity<Project> addCompetenceToProject(@PathVariable String id, @RequestBody Competence competence) {
		GlobalLogger.infoAction("Adding Competence", PROJECT,
				"Project ID: " + id + ", Competence: " + competence.getLibelle());
		Project updatedProject = projectService.addCompetenceToProject(id, competence);
		GlobalLogger.infoSuccess("Added Competence", PROJECT,
				"Project ID: " + id + ", Competence: " + competence.getLibelle());
		return ResponseEntity.ok(updatedProject);
	}

	@GetMapping("/{id}/competences")
	public ResponseEntity<List<Competence>> getCompetences(@PathVariable String id) {
		GlobalLogger.infoAction("Fetching Competences", PROJECT, "Project ID: " + id);
		List<Competence> competences = projectService.getCompetences(id);
		GlobalLogger.infoSuccess("Fetched Competences", PROJECT,
				"Project ID: " + id + ", Competence Count: " + competences.size());
		return ResponseEntity.ok(competences);
	}
}
