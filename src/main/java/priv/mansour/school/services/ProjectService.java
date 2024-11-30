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

	public Project addProject(Project project) {
		return projectRepository.save(project);
	}

	public List<Project> getAllProjects() {
		return projectRepository.findAll();
	}

	public Optional<Project> getProjectById(int id) {
		return projectRepository.findById(id);
	}

	public Project getProjectByLibelle(String libelle) {
		return projectRepository.findByLibelle(libelle);
	}

	public Project updateProject(int id, Project updatedProject) {
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

	public void deleteProjectById(int id) {
		projectRepository.deleteById(id);
	}

	public void addCompetenceToProject(int projectId, Competence competence) {
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
