package priv.mansour.school.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import priv.mansour.school.entity.Project;


@Repository
public interface ProjectRepository extends MongoRepository< Project, String> {
	Optional<Project> findByLibelle(String libelle);

}
