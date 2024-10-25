package priv.mansour.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import priv.mansour.school.entity.ProjectResult;

@Repository
public interface ProjectResultRepository extends JpaRepository<ProjectResult,Integer> {

}
