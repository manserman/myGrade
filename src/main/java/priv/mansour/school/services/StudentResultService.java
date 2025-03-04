package priv.mansour.school.services;

import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.mansour.school.entity.Competence;
import priv.mansour.school.entity.Student;
import priv.mansour.school.enums.ResultatEnum;
import priv.mansour.school.exceptions.DuplicateKeyException;
import priv.mansour.school.exceptions.ResourceNotFoundException;
import priv.mansour.school.logger.GlobalLogger;
import priv.mansour.school.repository.StudentRepository;

@Service
public class StudentResultService {

	private final StudentRepository studentRepository;

	@Autowired
	public StudentResultService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@Transactional
	public Student addResultToStudent(@NotBlank String studentId, Competence competence, ResultatEnum result) {
		GlobalLogger.infoCreate("StudentResult", "Adding result [" + result + "] for competence ["
				+ competence.getLibelle() + "] to student [" + studentId + "]");

		Student student = studentRepository.findById(studentId).orElseThrow(() -> {
			GlobalLogger.warnNotFound("Student", studentId);
			return new ResourceNotFoundException("Student", "addResult", "Student not found with ID: " + studentId);
		});

		boolean added = student.addResult(competence, result);
		if (!added) {
			GlobalLogger.warnDuplicate("StudentResult", studentId);
			throw new DuplicateKeyException("Competence [" + competence.getLibelle() + "] already exists for student ["
					+ studentId + "]. Use updateResult instead.", "StudentResult", studentId);
		}

		Student updatedStudent = studentRepository.save(student);
		GlobalLogger.infoSuccess("Added result [" + result + "] for competence [" + competence.getLibelle() + "]",
				"Student", studentId);
		return updatedStudent;
	}

	@Transactional
	public Student updateStudentResult(@NotBlank String studentId, Competence competence, ResultatEnum resultat) {
		GlobalLogger.infoUpdate("StudentResult", studentId, "Updating result [" + resultat + "] for competence ["
				+ competence.getLibelle() + "] in student [" + studentId + "]");

		Student student = studentRepository.findById(studentId).orElseThrow(() -> {
			GlobalLogger.warnNotFound("Student", studentId);
			return new ResourceNotFoundException("Student", "updateResult", "Student not found with ID: " + studentId);
		});

		boolean updated = student.updateResult(competence, resultat);
		if (!updated) {
			GlobalLogger.warnNotFound("StudentResult", studentId);
			throw new ResourceNotFoundException("StudentResult", "updateResult",
					"No existing result for competence " + competence.getLibelle());
		}

		Student updatedStudent = studentRepository.save(student);
		GlobalLogger.infoSuccess("Updated result [" + resultat + "] for competence [" + competence.getLibelle() + "]",
				"Student", studentId);
		return updatedStudent;
	}
}
