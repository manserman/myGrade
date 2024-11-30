package priv.mansour.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import priv.mansour.school.entity.Project;
import priv.mansour.school.entity.Teacher;
import priv.mansour.school.services.TeacherService;

import java.util.List;
import java.util.Optional;

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
		return ResponseEntity.ok(teacherService.addTeacher(teacher));
	}

	@GetMapping("/all")
	public ResponseEntity<List<Teacher>> getAllTeachers() {
		return ResponseEntity.ok(teacherService.getAllTeachers());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Teacher> getTeacherById(@PathVariable int id) {
		Optional<Teacher> teacher = teacherService.getTeacherById(id);
		return teacher.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/{id}/projects")
	public ResponseEntity<Teacher> addProjectToTeacher(@PathVariable int id, @RequestBody Project project) {
		try {
			return ResponseEntity.ok(teacherService.addProjectToTeacher(id, project));
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Teacher> updateTeacher(@PathVariable int id, @RequestBody Teacher updatedTeacher) {
		try {
			return ResponseEntity.ok(teacherService.updateTeacher(id, updatedTeacher));
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTeacherById(@PathVariable int id) {
		teacherService.deleteTeacherById(id);
		return ResponseEntity.noContent().build();
	}
}
