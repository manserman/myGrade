package priv.mansour.school.controller;

import static priv.mansour.school.utils.Constants.STUDENT;

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

import priv.mansour.school.entity.Student;
import priv.mansour.school.logger.GlobalLogger;
import priv.mansour.school.services.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

	private final StudentService studentService;

	@Autowired
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@PostMapping("/new")
	public ResponseEntity<Student> addStudent(@RequestBody Student student) {
		GlobalLogger.infoCreate(STUDENT, student);
		return ResponseEntity.ok(studentService.addStudent(student));
	}

	@GetMapping
	public ResponseEntity<List<Student>> getAllStudents() {
		GlobalLogger.infoReadAll(STUDENT);
		return ResponseEntity.ok(studentService.getAllStudents());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable String id) {
		GlobalLogger.infoRead(STUDENT, id);
		return ResponseEntity.ok(studentService.getStudentById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable String id, @RequestBody Student updatedStudent) {
		GlobalLogger.infoUpdate(STUDENT, id, updatedStudent);
		return ResponseEntity.ok(studentService.updateStudent(id, updatedStudent));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStudentById(@PathVariable String id) {
		GlobalLogger.infoDelete(STUDENT, id);
		studentService.deleteStudentById(id);
		return ResponseEntity.noContent().build();
	}
}
