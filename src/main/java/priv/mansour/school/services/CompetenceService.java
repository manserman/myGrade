package priv.mansour.school.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import priv.mansour.school.entity.Competence;
import priv.mansour.school.exceptions.ResourceNotFoundException;
import priv.mansour.school.repository.CompetenceRepository;

@Service
@Validated
public class CompetenceService {

	private final CompetenceRepository competenceRepository;

	@Autowired
	public CompetenceService(CompetenceRepository competenceRepository) {
		this.competenceRepository = competenceRepository;
	}

	public Competence addCompetence(@Valid Competence competence) {
		if (competenceRepository.findByLibelle(competence.getLibelle()) != null) {
			throw new RuntimeException("Une compétence avec ce libellé existe déjà.");
		}
		return competenceRepository.save(competence);
	}

	public List<Competence> getAllCompetences() {
		return competenceRepository.findAll();
	}

	public Competence getCompetenceById(@NotBlank String id) {
		return competenceRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Imposisble de toruver la compétence: " + id));
	}

	public Competence getCompetenceByLibelle(@NotBlank String libelle) {
		return competenceRepository.findByLibelle(libelle)
				.orElseThrow(() -> new ResourceNotFoundException("Imposisble de toruver la compétence: " + libelle));
	}

	public Competence updateCompetence(@NotBlank String id, @Valid Competence updatedCompetence) {
		Competence competence = getCompetenceById(id);
		competence.setLibelle(updatedCompetence.getLibelle());
		competence.setDescription(updatedCompetence.getDescription());
		return competenceRepository.save(competence);

	}

	public void deleteCompetenceById(@NotBlank String id) {
		getCompetenceById(id);
		competenceRepository.deleteById(id);
	}
}
