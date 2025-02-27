package priv.mansour.school.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import priv.mansour.school.entity.Teacher;

@Repository
public interface TeacherRepository extends MongoRepository<Teacher, String>, IFindByEmail<Teacher> {

}