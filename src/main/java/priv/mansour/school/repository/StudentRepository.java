package priv.mansour.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import priv.mansour.school.entity.Student;

@Repository
public interface StudentRepository extends  JpaRepository<Student, Integer> {

}
