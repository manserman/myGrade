package priv.mansour.school.services;

import static priv.mansour.school.utils.Constants.PROJECT;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import priv.mansour.school.entity.Competence;
import priv.mansour.school.entity.Project;
import priv.mansour.school.exceptions.DuplicateKeyException;
import priv.mansour.school.exceptions.ResourceNotFoundException;
import priv.mansour.school.logger.GlobalLogger;
import priv.mansour.school.repository.ProjectRepository;

@Validated
@Service
public class ProjectService {

	private final ProjectRepository projectRepository;

	@Autowired
	public ProjectService(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}

	public Project addProject(@Valid Project project) {
		GlobalLogger.infoAction("Saving", PROJECT, project);
		Project savedProject = projectRepository.save(project);
		GlobalLogger.infoSuccess("Saved", PROJECT, savedProject);
		return savedProject;
	}

	public List<Project> getAllProjects() {
		GlobalLogger.infoAction("Fetching all", PROJECT, "Retrieving all projects from database");
		List<Project> projects = projectRepository.findAll();
		GlobalLogger.infoSuccess("Fetched all", PROJECT, projects.size() + " projects found");
		return projects;
	}

	public Project getProjectById(@NotBlank String id) {
		GlobalLogger.infoAction("Fetching", PROJECT, "ID: " + id);
		return projectRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(PROJECT, "READ", "Project not found for ID: " + id));
	}

	public Project getProjectByLibelle(@NotBlank String libelle) {
		GlobalLogger.infoAction("Fetching", PROJECT, "Libelle: " + libelle);
		return projectRepository.findByLibelle(libelle).orElseThrow(
				() -> new ResourceNotFoundException(PROJECT, "READ", "Project not found for libelle: " + libelle));
	}

	public Project updateProject(@NotBlank String id, @Valid Project updatedProject) {
		GlobalLogger.infoAction("Updating", PROJECT, "ID: " + id);

		Project project = getProjectById(id);

		project.setCompetences(updatedProject.getCompetences());

		Project updated = projectRepository.save(project);
		GlobalLogger.infoSuccess("Updated", PROJECT, id);
		return updated;
	}

	public void deleteProjectById(@NotBlank String id) {
		GlobalLogger.infoAction("Deleting", PROJECT, "ID: " + id);

		if (!projectRepository.existsById(id)) {
			throw new ResourceNotFoundException(PROJECT, "DELETE", "Project not found for ID: " + id);
		}

		projectRepository.deleteById(id);
		GlobalLogger.infoSuccess("Deleted", PROJECT, id);
	}

	public Project addCompetenceToProject(@NotBlank String projectId, @Valid Competence competence) {
		GlobalLogger.infoAction("Adding Competence", PROJECT,
				"Project ID: " + projectId + " Competence: " + competence.getLibelle());

		Project project = getProjectById(projectId);

		if (project.getCompetences() == null) {
			project.setCompetences(new ArrayList<>());
		}

		boolean projectExists = project.getCompetences().stream()
				.anyMatch(existingCompetence -> existingCompetence.getLibelle().equals(competence.getLibelle()));

		if (projectExists) {
			GlobalLogger.warnDuplicate("Competence",
					"Competence [" + competence.getLibelle() + "] already exists in project [" + projectId + "]");
			throw new DuplicateKeyException("Competence already exists in this project.", "Project", projectId);
		}

		project.getCompetences().add(competence);
		Project updatedProject = projectRepository.save(project);

		GlobalLogger.infoSuccess("Added Competence", PROJECT,
				"Project ID: " + projectId + " Competence: " + competence.getLibelle());

		return updatedProject;
	}

	public List<Competence> getCompetences(@NotBlank String projectId) {
		GlobalLogger.infoAction("Fetching Competences", PROJECT, "Project ID: " + projectId);

		Project project = getProjectById(projectId);

		List<Competence> competences = project.getCompetences();

		if (competences == null || competences.isEmpty()) {
			GlobalLogger.warnNotFound("Competences", "No competences found for project [" + projectId + "]");
		} else {
			GlobalLogger.infoSuccess("Fetched Competences", PROJECT,
					"Project ID: " + projectId + " - " + competences.size() + " competences found");
		}

		return competences;
	}

}
