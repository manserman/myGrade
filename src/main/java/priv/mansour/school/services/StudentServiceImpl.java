package priv.mansour.school.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import priv.mansour.school.entity.Student;
import priv.mansour.school.exceptions.ResourceNotFoundException;
import priv.mansour.school.logger.GlobalLogger;
import priv.mansour.school.repository.StudentRepository;

import static priv.mansour.school.utils.Constants.STUDENT;

@Service
public class StudentServiceImpl implements IUserService<Student> {

	private final StudentRepository studentRepository;

	@Autowired
	public StudentServiceImpl(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public Student add(Student student) {
		GlobalLogger.infoCreate("Adding new student: {}", student);
		Student savedStudent = studentRepository.save(student);
		GlobalLogger.infoCreate("Student added successfully with ID: {}", savedStudent.getId());
		return savedStudent;
	}

	public List<Student> getAll() {
		GlobalLogger.infoReadAll(STUDENT);

		List<Student> students = studentRepository.findAll();

		GlobalLogger.infoReadAll("Fetched {} students "+ students.size());

		return students;
	}

	public Student getById( String id) {
		GlobalLogger.infoRead("Fetching student by ID: {}", id);
		return studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Étudiant non trouvé avec l'ID : " + id));
	}

	public Student update(@NotBlank String id, @Valid Student updatedStudent) {
		GlobalLogger.infoUpdate(STUDENT,id,updatedStudent);
		Student student = this.getById(id);

		student.setNom(updatedStudent.getNom());
		student.setPrenom(updatedStudent.getPrenom());

		Student savedStudent = studentRepository.save(student);
		GlobalLogger.infoUpdate(STUDENT,id, savedStudent);

		return savedStudent;
	}

	public void deleteById(@NotBlank String id) {
		GlobalLogger.infoDelete("Deleting student with ID: {}", id);
		if (!studentRepository.existsById(id)) {
			GlobalLogger.warnNotFound(STUDENT, id);
			throw new ResourceNotFoundException("Étudiant non trouvé avec l'ID : " + id);
		}

		studentRepository.deleteById(id);
		GlobalLogger.infoSuccess("DELETE","STUDENT", id);
	}

	@Override
	public Student findByEmail(String mail) {
		GlobalLogger.infoRead("Fetching student by ID: {}", mail);
		return studentRepository.findByMail(mail)
				.orElseThrow(() -> new ResourceNotFoundException("Étudiant non trouvé avec le mail : " + mail));
	}
}