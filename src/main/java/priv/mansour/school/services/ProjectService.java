package priv.mansour.school.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.mansour.school.entity.Project;
import priv.mansour.school.entity.Competence;
import priv.mansour.school.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

	private final ProjectRepository projectRepository;

	@Autowired
	public ProjectService(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}

	// Ajouter un projet
	public Project addProject(Project project) {
		return projectRepository.save(project);
	}

	// Récupérer tous les projets
	public List<Project> getAllProjects() {
		return projectRepository.findAll();
	}

	// Récupérer un projet par ID
	public Optional<Project> getProjectById(Long id) {
		return projectRepository.findById(id);
	}

	// Récupérer un projet par libellé
	public Project getProjectByLibelle(String libelle) {
		return projectRepository.findByLibelle(libelle);
	}

	// Mettre à jour un projet
	public Project updateProject(Long id, Project updatedProject) {
		Optional<Project> existingProject = projectRepository.findById(id);
		if (existingProject.isPresent()) {
			Project project = existingProject.get();
			project.setLibelle(updatedProject.getLibelle());
			project.setDescription(updatedProject.getDescription());
			project.setCompetences(updatedProject.getCompetences());
			project.setTeacher(updatedProject.getTeacher());
			return projectRepository.save(project);
		}
		throw new RuntimeException("Projet non trouvé avec l'ID : " + id);
	}

	// Supprimer un projet par ID
	public void deleteProjectById(Long id) {
		projectRepository.deleteById(id);
	}

	// Ajouter une compétence à un projet
	public void addCompetenceToProject(Long projectId, Competence competence) {
		Optional<Project> projectOptional = projectRepository.findById(projectId);
		if (projectOptional.isPresent()) {
			Project project = projectOptional.get();
			if (!project.getCompetences().contains(competence)) {
				project.getCompetences().add(competence);
				projectRepository.save(project);
			}
		} else {
			throw new RuntimeException("Projet non trouvé avec l'ID : " + projectId);
		}
	}
}
