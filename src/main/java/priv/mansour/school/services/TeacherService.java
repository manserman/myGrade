package priv.mansour.school.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import priv.mansour.school.entity.Project;
import priv.mansour.school.entity.Teacher;
import priv.mansour.school.repository.TeacherRepository;

@Service
public class TeacherService {
	private final TeacherRepository teacherRepository;

	@Autowired
	public TeacherService(TeacherRepository teacherRepository) {
		this.teacherRepository = teacherRepository;
	}

	public Teacher addTeacher(Teacher teacher) {
		return teacherRepository.save(teacher);
	}

	public List<Teacher> getAllTeachers() {
		return teacherRepository.findAll();
	}

	public Optional<Teacher> getTeacherById(int id) {
		return teacherRepository.findById(id);
	}

	public Teacher addProjectToTeacher(int teacherId, Project project) {
		Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
		if (optionalTeacher.isEmpty()) {
			throw new RuntimeException("Enseignant non trouvé avec l'ID : " + teacherId);
		}

		Teacher teacher = optionalTeacher.get();
		List<Project> projets = teacher.getProjets();
		if (!projets.contains(project)) {
			projets.add(project);
		}
		return teacherRepository.save(teacher);
	}

	public Teacher updateTeacher(int id, Teacher updatedTeacher) {
		Optional<Teacher> existingTeacher = teacherRepository.findById(id);
		if (existingTeacher.isPresent()) {
			Teacher teacher = existingTeacher.get();
			teacher.setNom(updatedTeacher.getNom());
			teacher.setPrenom(updatedTeacher.getPrenom());
			teacher.setProjets(updatedTeacher.getProjets());
			return teacherRepository.save(teacher);
		}
		throw new RuntimeException("Enseignant non trouvé avec l'ID : " + id);
	}

	public void deleteTeacherById(int id) {
		teacherRepository.deleteById(id);
	}
}
