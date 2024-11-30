package priv.mansour.school.services;

import java.util.List;
import java.util.Optional;

import javax.naming.spi.DirStateFactory.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import priv.mansour.school.entity.Competence;
import priv.mansour.school.entity.Project;
import priv.mansour.school.entity.Resultat;
import priv.mansour.school.entity.ResultatEnum;
import priv.mansour.school.entity.Student;
import priv.mansour.school.repository.StudentRepository;

@Service
public class StudentService {

	private final StudentRepository studentRepository;

	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	// Ajouter un étudiant
	public Student addStudent(Student student) {
		return studentRepository.save(student);
	}

	// Récupérer tous les étudiants
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	// Récupérer un étudiant par ID
	public Optional<Student> getStudentById(int id) {
		return studentRepository.findById(id);
	}

	// Mettre à jour un étudiant
	public Student updateStudent(int id, Student updatedStudent) {
		Optional<Student> existingStudent = studentRepository.findById(id);
		if (existingStudent.isPresent()) {
			Student student = existingStudent.get();
			student.setNom(updatedStudent.getNom());
			student.setPrenom(updatedStudent.getPrenom());
			return studentRepository.save(student);
		}
		throw new RuntimeException("Student non trouvé avec l'ID : " + id);
	}

	public Student addStudentProject(int id, Project project) {
		Optional<Student> existingStudent = studentRepository.findById(id);
		if (existingStudent.isPresent()) {
			Student student = existingStudent.get();
			student.addProject(project);
			return studentRepository.save(student);
		}
		throw new RuntimeException("Student non trouvé avec l'ID : " + id);
	}

	public Student addStudentResult(int id, Competence comptence, ResultatEnum result) {
		Optional<Student> existingStudent = studentRepository.findById(id);
		if (existingStudent.isPresent()) {
			Student student = existingStudent.get();
			student.addOrUpdateResult(comptence, result);
			return studentRepository.save(student);
		}
		throw new RuntimeException("Student non trouvé avec l'ID : " + id);
	}

	// Supprimer un étudiant par ID
	public void deleteStudentById(int id) {
		studentRepository.deleteById(id);
	}

}
