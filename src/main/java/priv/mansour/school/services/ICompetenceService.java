package priv.mansour.school.services;

import jakarta.validation.constraints.NotBlank;
import priv.mansour.school.entity.Competence;

public interface ICompetenceService extends ICrudService<Competence,String> {
    Competence getCompetenceByLibelle(@NotBlank String libelle);
}
