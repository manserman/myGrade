package priv.mansour.school.services;

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
		return projectRepository.save(project);
	}

	public List<Project> getAllProjects() {
		return projectRepository.findAll();
	}

	public Project getProjectById(@NotBlank String id) {
		return projectRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Projet non trouvé avec l'ID : " + id));
	}

	public Project getProjectByLibelle(@NotBlank String libelle) {
		return projectRepository.findByLibelle(libelle)
				.orElseThrow(() -> new ResourceNotFoundException("Projet non trouvé avec le libelle : " + libelle));
	}

	public Project updateProject(@NotBlank String id, @Valid Project updatedProject) {
		Project project = getProjectById(id);
		project.setLibelle(updatedProject.getLibelle());
		project.setDescription(updatedProject.getDescription());
		project.setCompetences(updatedProject.getCompetences());
		project.setTeacher(updatedProject.getTeacher());
		return projectRepository.save(project);
	}

	public void deleteProjectById(@NotBlank String id) {
		if (!projectRepository.existsById(id)) {
			throw new ResourceNotFoundException("Projet non trouvé avec l'ID : " + id);
		}

		projectRepository.deleteById(id);
	}

	public Project addCompetenceToProject(@NotBlank String projectId, @Valid Competence competence) {

		Project project = getProjectById(projectId);
		if (project.getCompetences().contains(competence)) {
			throw new DuplicateKeyException(
					"Le projet " + projectId + " Contient déjà la compétence " + competence.getId());
		}
		project.getCompetences().add(competence);
		return projectRepository.save(project);

	}
}
