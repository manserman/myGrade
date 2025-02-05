package priv.mansour.school.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import priv.mansour.school.entity.Competence;

public interface CompetenceRepository extends MongoRepository<Competence, String> {
	Optional<Competence> findByLibelle(String libelle);

}
