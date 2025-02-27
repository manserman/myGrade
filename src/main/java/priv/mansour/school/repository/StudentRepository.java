package priv.mansour.school.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import priv.mansour.school.entity.Student;

@Repository
public interface StudentRepository extends MongoRepository<Student, String>, IFindByEmail<Student> {


}
