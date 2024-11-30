package priv.mansour.school.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import priv.mansour.school.entity.Competence;
import priv.mansour.school.entity.Project;
import priv.mansour.school.entity.ResultatEnum;
import priv.mansour.school.entity.Student;
import priv.mansour.school.repository.CompetenceRepository;
import priv.mansour.school.repository.ProjectRepository;
import priv.mansour.school.repository.StudentRepository;

@Service
public class StudentService {

	private final StudentRepository studentRepository;
	private final ProjectRepository projectRepository;

	@Autowired
	public StudentService(StudentRepository studentRepository, ProjectRepository projectRepository) {
		this.studentRepository = studentRepository;
		this.projectRepository = projectRepository;
	}

	public Student addStudent(Student student) {
		return studentRepository.save(student);
	}

	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	public Optional<Student> getStudentById(int id) {
		return studentRepository.findById(id);
	}

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

	public Student addStudentResult(int id, String projetlib, ResultatEnum result) {
		Optional<Project> existingProject = projectRepository.findById(id);
		if (existingProject.isPresent()) {
			Student student = null;
			for (Competence c : existingProject.get().getCompetences()) {
				student = addStudentResult(id, c, result);
			}
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

	public void deleteStudentById(int id) {
		studentRepository.deleteById(id);
	}

}
