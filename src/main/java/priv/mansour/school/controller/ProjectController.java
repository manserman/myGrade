package priv.mansour.school.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import priv.mansour.school.entity.Competence;
import priv.mansour.school.entity.Project;
import priv.mansour.school.logger.GlobalLogger;
import priv.mansour.school.services.IProjectService;

import java.util.List;

import static priv.mansour.school.utils.Constants.PROJECT;

@Validated
@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final IProjectService projectService;

    @Autowired
    public ProjectController(IProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<Project> addProject(@Valid @RequestBody Project project) {
        GlobalLogger.infoCreate(PROJECT, "Creating new project: " + project.getLibelle());
        Project createdProject = projectService.add(project);
        GlobalLogger.infoSuccess("Created", PROJECT, "Project ID: " + createdProject.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        GlobalLogger.infoReadAll(PROJECT);
        List<Project> projects = projectService.getAll();
        GlobalLogger.infoSuccess("Fetched all", PROJECT, "Total projects: " + projects.size());
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable @NotBlank String id) {
        GlobalLogger.infoRead(PROJECT, "Fetching project by ID: " + id);
        Project project = projectService.getById(id);
        GlobalLogger.infoSuccess("Fetched by ID", PROJECT, "Project ID: " + project.getId());
        return ResponseEntity.ok(project);
    }

    @GetMapping("/by-libelle")
    public ResponseEntity<Project> getProjectByLibelle(@RequestParam @NotBlank String libelle) {
        GlobalLogger.infoRead(PROJECT, "Fetching project by Libelle: " + libelle);
        Project project = projectService.findByLibelle(libelle);
        GlobalLogger.infoSuccess("Fetched by Libelle", PROJECT, "Project Libelle: " + project.getLibelle());
        return ResponseEntity.ok(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable @NotBlank String id, @Valid @RequestBody Project updatedProject) {
        GlobalLogger.infoUpdate(PROJECT, id, updatedProject);
        Project updated = projectService.update(id, updatedProject);
        GlobalLogger.infoSuccess("Updated", PROJECT, "Project ID: " + updated.getId());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectById(@PathVariable @NotBlank String id) {
        GlobalLogger.infoDelete(PROJECT, "Deleting project ID: " + id);
        projectService.deleteById(id);
        GlobalLogger.infoSuccess("Deleted", PROJECT, "Project ID: " + id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/competences")
    public ResponseEntity<Project> addCompetenceToProject(@PathVariable @NotBlank String id, @Valid @RequestBody Competence competence) {
        GlobalLogger.infoAction("Adding Competence", PROJECT, "Project ID: " + id + ", Competence: " + competence.getLibelle());
        Project updatedProject = projectService.addCompetenceToProject(id, competence);
        GlobalLogger.infoSuccess("Added Competence", PROJECT, "Project ID: " + id + ", Competence: " + competence.getLibelle());
        return ResponseEntity.ok(updatedProject);
    }

    @GetMapping("/{id}/competences")
    public ResponseEntity<List<Competence>> getCompetences(@PathVariable @NotBlank String id) {
        GlobalLogger.infoAction("Fetching Competences", PROJECT, "Project ID: " + id);
        List<Competence> competences = projectService.getAllCompetences(id);
        GlobalLogger.infoSuccess("Fetched Competences", PROJECT, "Project ID: " + id + ", Competence Count: " + competences.size());
        return ResponseEntity.ok(competences);
    }
}
