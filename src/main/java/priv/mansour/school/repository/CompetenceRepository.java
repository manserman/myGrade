package priv.mansour.school.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import priv.mansour.school.entity.Competence;

public interface CompetenceRepository extends MongoRepository<Competence, Integer> {
	Competence findByLibelle(String libelle);

}
