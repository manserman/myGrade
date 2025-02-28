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
import priv.mansour.school.repository.ProjectRepository;

@Service
@Validated
public class ProjectServiceImpl implements IProjectService {

	private final ProjectRepository projectRepository;

	@Autowired
	public ProjectServiceImpl(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}

	@Override
	public Project add(@Valid Project project) {
		try {
			return projectRepository.save(project);
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateKeyException("Project already exists", PROJECT, project.getId());
		}
	}

	@Override
	public List<Project> getAll() {
		return projectRepository.findAll();
	}

	@Override
	public Project getById(@NotBlank String id) {
		return projectRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(PROJECT, "READ", "Project not found for ID: " + id));
	}

	@Override
	public Project findByLibelle(@NotBlank String libelle) {
		return projectRepository.findByLibelle(libelle)
				.orElseThrow(() -> new ResourceNotFoundException(PROJECT, "READ", "Project not found for libelle: " + libelle));
	}

	@Override
	public Project update(@NotBlank String id, @Valid Project updatedProject) {
		Project project = getById(id);
		project.setCompetences(updatedProject.getCompetences());
		return projectRepository.save(project);
	}

	@Override
	public void deleteById(@NotBlank String id) {
		if (!projectRepository.existsById(id)) {
			throw new ResourceNotFoundException(PROJECT, "DELETE", "Project not found for ID: " + id);
		}
		projectRepository.deleteById(id);
	}

	public Project addCompetenceToProject(@NotBlank String projectId, @Valid Competence competence) {
		Project project = getById(projectId);

		if (project.getCompetences() == null) {
			project.setCompetences(new ArrayList<>());
		}

		boolean competenceExists = project.getCompetences().stream()
				.anyMatch(existingCompetence -> existingCompetence.getLibelle().equals(competence.getLibelle()));

		if (competenceExists) {
			throw new DuplicateKeyException("Competence already exists in this project.", "Project", projectId);
		}

		project.getCompetences().add(competence);
		return projectRepository.save(project);
	}

	public List<Competence> getAllCompetences(@NotBlank String projectId) {
		Project project = getById(projectId);
		return project.getCompetences() == null ? new ArrayList<>() : project.getCompetences();
	}
}

