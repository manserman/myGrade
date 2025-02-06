package priv.mansour.school.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import priv.mansour.school.entity.Student;
import priv.mansour.school.exceptions.ResourceNotFoundException;
import priv.mansour.school.repository.StudentRepository;

@Slf4j
@Validated
@Service
public class StudentService {

	private final StudentRepository studentRepository;

	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public Student addStudent(@Valid Student student) {
		log.info("Adding new student: {}", student);
		Student savedStudent = studentRepository.save(student);
		log.info("Student added successfully with ID: {}", savedStudent.getId());
		return savedStudent;
	}

	public List<Student> getAllStudents() {
		log.info("Fetching all students");

		List<Student> students = studentRepository.findAll();

		log.info("Fetched {} students", students.size());

		return students;
	}

	public Student getStudentById(@NotBlank String id) {
		log.info("Fetching student by ID: {}", id);
		return studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Étudiant non trouvé avec l'ID : " + id));
	}

	public Student updateStudent(@NotBlank String id, @Valid Student updatedStudent) {
		log.info("Updating student with ID: {}", id);
		Student student = getStudentById(id);

		student.setNom(updatedStudent.getNom());
		student.setPrenom(updatedStudent.getPrenom());

		Student savedStudent = studentRepository.save(student);
		log.info("Student updated successfully: {}", savedStudent);
		return savedStudent;
	}

	public void deleteStudentById(@NotBlank String id) {
		log.info("Deleting student with ID: {}", id);
		if (!studentRepository.existsById(id)) {
			log.error("Student not found with ID: {}", id);
			throw new ResourceNotFoundException("Étudiant non trouvé avec l'ID : " + id);
		}

		studentRepository.deleteById(id);
		log.info("Student deleted successfully with ID: {}", id);
	}
}