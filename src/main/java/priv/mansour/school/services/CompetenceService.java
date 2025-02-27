package priv.mansour.school.services;

import static priv.mansour.school.utils.Constants.COMPETENCE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import priv.mansour.school.entity.Competence;
import priv.mansour.school.exceptions.ResourceNotFoundException;
import priv.mansour.school.logger.GlobalLogger;
import priv.mansour.school.repository.CompetenceRepository;

@Service
@Validated
public class CompetenceService implements ICompetenceService {

    private final CompetenceRepository competenceRepository;

    @Autowired
    public CompetenceService(CompetenceRepository competenceRepository) {
        this.competenceRepository = competenceRepository;
    }

    @Override
    public Competence add(@Valid Competence competence) {
        GlobalLogger.infoAction("Saving", COMPETENCE, competence);
        Competence savedCompetence = competenceRepository.save(competence);
        GlobalLogger.infoSuccess("Saved", COMPETENCE, savedCompetence);
        return savedCompetence;
    }

    @Override
    public List<Competence> getAll() {
        GlobalLogger.infoAction("Fetching all", COMPETENCE, "Retrieving all competences from database");
        List<Competence> competences = competenceRepository.findAll();
        GlobalLogger.infoSuccess("Fetched all", COMPETENCE, competences.size() + " competences found");
        return competences;
    }

    @Override
    public Competence getById(@NotBlank String id) {
        GlobalLogger.infoAction("Fetching", COMPETENCE, "ID: " + id);
        return competenceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(COMPETENCE, "READ", "Competence not found for ID: " + id));
    }

    @Override
    public Competence getCompetenceByLibelle(@NotBlank String libelle) {
        GlobalLogger.infoAction("Fetching", COMPETENCE, "Libelle: " + libelle);
        return competenceRepository.findByLibelle(libelle).orElseThrow(() -> new ResourceNotFoundException(COMPETENCE,
                "READ", "Competence not found for libelle: " + libelle));
    }

    @Override
    public Competence update(@NotBlank String id, @Valid Competence updatedCompetence) {
        GlobalLogger.infoAction("Updating", COMPETENCE, "ID: " + id);

        Competence competence = getById(id);
        Competence updated = competenceRepository.save(competence);
        GlobalLogger.infoSuccess("Updated", COMPETENCE, id);
        return updated;
    }

    @Override
    public void deleteById(@NotBlank String id) {
        GlobalLogger.infoAction("Deleting", COMPETENCE, "ID: " + id);

        if (!competenceRepository.existsById(id)) {
            throw new ResourceNotFoundException(COMPETENCE, "DELETE", "Competence not found for ID: " + id);
        }

        competenceRepository.deleteById(id);
        GlobalLogger.infoSuccess("Deleted", COMPETENCE, id);
    }
}
