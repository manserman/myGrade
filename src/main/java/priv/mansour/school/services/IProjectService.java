package priv.mansour.school.services;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import priv.mansour.school.entity.Competence;
import priv.mansour.school.entity.Project;

import java.util.List;

public interface IProjectService extends ICrudService<Project, String>, ILibelleService<Project> {
    public List<Competence> getAllCompetences(@NotBlank String projectId);

    public Project addCompetenceToProject(@NotBlank String projectId, @Valid Competence competence);
}
