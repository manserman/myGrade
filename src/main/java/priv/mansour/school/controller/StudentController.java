package priv.mansour.school.controller;

import java.net.http.HttpRequest;
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

import lombok.extern.slf4j.Slf4j;
import priv.mansour.school.entity.Student;
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
	public ResponseEntity<Student> addStudent(@RequestBody Student student, HttpRequest request) {
		
		return ResponseEntity.ok(studentService.addStudent(student));
	}

	@GetMapping("/all")
	public ResponseEntity<List<Student>> getAllStudents() {
		log.info("Received GET request to fetch all the  students");
		return ResponseEntity.ok(studentService.getAllStudents());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable String id) {
		log.info("Received GET request to fetch  the  student with ID: {}", id);
		return ResponseEntity.ok(studentService.getStudentById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable String id, @RequestBody Student updatedStudent) {
		log.info("Received PUT request to update  the  student with ID: {}", id);
		return ResponseEntity.ok(studentService.updateStudent(id, updatedStudent));

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteStudentById(@PathVariable String id) {
		log.info("Received DELETE request to delete  the  student with ID: {}", id);
		studentService.deleteStudentById(id);
		return ResponseEntity.noContent().build();
	}
}
