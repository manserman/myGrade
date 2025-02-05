package priv.mansour.school.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import priv.mansour.school.entity.Project;


@Repository
public interface ProjectRepository extends MongoRepository< Project, String> {
	Project findByLibelle(String libelle);

}
