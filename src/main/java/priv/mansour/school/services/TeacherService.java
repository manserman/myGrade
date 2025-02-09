package priv.mansour.school.services;

import static priv.mansour.school.utils.Constants.TEACHER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import priv.mansour.school.entity.Teacher;
import priv.mansour.school.exceptions.ResourceNotFoundException;
import priv.mansour.school.logger.GlobalLogger;
import priv.mansour.school.repository.TeacherRepository;

@Service
@Validated
public class TeacherService {

	private final TeacherRepository teacherRepository;

	@Autowired
	public TeacherService(TeacherRepository teacherRepository) {
		this.teacherRepository = teacherRepository;
	}

	public Teacher addTeacher(@Valid Teacher teacher) {
		GlobalLogger.infoAction("Saving", TEACHER, teacher);
		Teacher savedTeacher = teacherRepository.save(teacher);
		GlobalLogger.infoSuccess("Saved", TEACHER, savedTeacher);
		return savedTeacher;
	}

	public List<Teacher> getAllTeachers() {
		GlobalLogger.infoAction("Fetching all", TEACHER, "Retrieving all teachers from database");
		List<Teacher> teachers = teacherRepository.findAll();
		GlobalLogger.infoSuccess("Fetched all", TEACHER, teachers.size() + " teachers found");
		return teachers;
	}

	public Teacher getTeacherById(@NotBlank String id) {
		GlobalLogger.infoAction("Fetching", TEACHER, "ID: " + id);
		return teacherRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(TEACHER, "READ", "Teacher not found for ID: " + id));
	}

	public void deleteTeacherById(@NotBlank String id) {
		GlobalLogger.infoAction("Deleting", TEACHER, "ID: " + id);

		if (!teacherRepository.existsById(id)) {
			throw new ResourceNotFoundException(TEACHER, "DELETE", "Teacher not found for ID: " + id);
		}

		teacherRepository.deleteById(id);
		GlobalLogger.infoSuccess("Deleted", TEACHER, id);
	}
}
