package priv.mansour.school.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import priv.mansour.school.entity.Competence;
import priv.mansour.school.entity.Student;
import priv.mansour.school.enums.ResultatEnum;
import priv.mansour.school.logger.GlobalLogger;
import priv.mansour.school.services.StudentResultService;

@Validated
@RestController
@RequestMapping("/students/results")
public class StudentResultController {

	private final StudentResultService studentResultService;

	@Autowired
	public StudentResultController(StudentResultService studentResultService) {
		this.studentResultService = studentResultService;
	}

	@PostMapping("/{studentId}/add")
	public ResponseEntity<Student> addResultToStudent(@PathVariable @NotBlank String studentId,
			@RequestBody @Valid Competence competence, @RequestParam ResultatEnum resultat) {

		GlobalLogger.infoCreate("StudentResult", "Received request to add result [" + resultat + "] for competence ["
				+ competence.getLibelle() + "] to student [" + studentId + "]");

		Student updatedStudent = studentResultService.addResultToStudent(studentId, competence, resultat);

		GlobalLogger.infoSuccess(
				"Successfully added result [" + resultat + "] for competence [" + competence.getLibelle() + "]",
				"Student", studentId);

		return ResponseEntity.ok(updatedStudent);
	}

	@PutMapping("/{studentId}/update")
	public ResponseEntity<Student> updateStudentResult(@PathVariable @NotBlank String studentId,
			@RequestBody @Valid Competence competence, @RequestParam ResultatEnum resultat) {

		GlobalLogger.infoUpdate("StudentResult", studentId, "Received request to update result [" + resultat
				+ "] for competence [" + competence.getLibelle() + "] in student [" + studentId + "]");

		Student updatedStudent = studentResultService.updateStudentResult(studentId, competence, resultat);

		GlobalLogger.infoSuccess(
				"Successfully updated result [" + resultat + "] for competence [" + competence.getLibelle() + "]",
				"Student", studentId);

		return ResponseEntity.ok(updatedStudent);
	}
}
