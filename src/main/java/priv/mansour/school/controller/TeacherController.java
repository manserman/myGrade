package priv.mansour.school.controller;

import static priv.mansour.school.utils.Constants.TEACHER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import priv.mansour.school.entity.Project;
import priv.mansour.school.entity.Teacher;
import priv.mansour.school.logger.GlobalLogger;
import priv.mansour.school.services.TeacherService;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

	private final TeacherService teacherService;

	@Autowired
	public TeacherController(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@PostMapping("/new")
	public ResponseEntity<Teacher> addTeacher(@RequestBody Teacher teacher) {
		GlobalLogger.infoCreate(TEACHER, teacher);
		return ResponseEntity.ok(teacherService.addTeacher(teacher));
	}

	@GetMapping("/all")
	public ResponseEntity<List<Teacher>> getAllTeachers() {
		GlobalLogger.infoReadAll(TEACHER);
		return ResponseEntity.ok(teacherService.getAllTeachers());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Teacher> getTeacherById(@PathVariable String id) {
		GlobalLogger.infoRead(TEACHER, id);
		return ResponseEntity.ok(teacherService.getTeacherById(id));
	}

	@PostMapping("/{id}/projects")
	public ResponseEntity<Teacher> addProjectToTeacher(@PathVariable String id, @RequestBody Project project) {
		GlobalLogger.infoAction("Adding Project", TEACHER, "Teacher ID: " + id + " Project: " + project);
		return ResponseEntity.ok(teacherService.addProjectToTeacher(id, project));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Teacher> updateTeacher(@PathVariable String id, @RequestBody Teacher updatedTeacher) {
		GlobalLogger.infoUpdate(TEACHER, id, updatedTeacher);
		return ResponseEntity.ok(teacherService.updateTeacher(id, updatedTeacher));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTeacherById(@PathVariable String id) {
		GlobalLogger.infoDelete(TEACHER, id);
		teacherService.deleteTeacherById(id);
		return ResponseEntity.noContent().build();
	}
}
