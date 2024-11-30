package priv.mansour.school.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import priv.mansour.school.entity.Competence;
import priv.mansour.school.repository.CompetenceRepository;

@Service
public class CompetenceService {

    private final CompetenceRepository competenceRepository;

    @Autowired
    public CompetenceService(CompetenceRepository competenceRepository) {
        this.competenceRepository = competenceRepository;
    }

    // Ajouter une compétence
    public Competence addCompetence(Competence competence) {
        if (competenceRepository.findByLibelle(competence.getLibelle()) != null) {
            throw new RuntimeException("Une compétence avec ce libellé existe déjà.");
        }
        return competenceRepository.save(competence);
    }

    // Récupérer toutes les compétences
    public List<Competence> getAllCompetences() {
        return competenceRepository.findAll();
    }

    // Récupérer une compétence par ID
    public Optional<Competence> getCompetenceById(int id) {
        return competenceRepository.findById(id);
    }

    // Récupérer une compétence par libellé
    public Competence getCompetenceByLibelle(String libelle) {
        return competenceRepository.findByLibelle(libelle);
    }

    // Mettre à jour une compétence
    public Competence updateCompetence(int id, Competence updatedCompetence) {
        Optional<Competence> existingCompetence = competenceRepository.findById(id);
        if (existingCompetence.isPresent()) {
            Competence competence = existingCompetence.get();
            competence.setLibelle(updatedCompetence.getLibelle());
            competence.setDescription(updatedCompetence.getDescription());
            return competenceRepository.save(competence);
        }
        throw new RuntimeException("Competence non trouvée avec l'ID : " + id);
    }

    // Supprimer une compétence par ID
    public void deleteCompetenceById(int id) {
        competenceRepository.deleteById(id);
    }
}
