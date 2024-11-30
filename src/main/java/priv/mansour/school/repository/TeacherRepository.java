package priv.mansour.school.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import priv.mansour.school.entity.Teacher;

@Repository
public interface TeacherRepository extends MongoRepository<Teacher, Integer> {

}
