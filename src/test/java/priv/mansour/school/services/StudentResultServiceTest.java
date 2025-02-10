package priv.mansour.school.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import priv.mansour.school.entity.Competence;
import priv.mansour.school.entity.Student;
import priv.mansour.school.enums.ResultatEnum;
import priv.mansour.school.exceptions.DuplicateKeyException;
import priv.mansour.school.exceptions.ResourceNotFoundException;
import priv.mansour.school.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
public class StudentResultServiceTest {

	@Mock
	StudentRepository studentRepository;

	@InjectMocks
	StudentResultService studentResultService;

	Student student;
	Competence competence;

	@BeforeEach
	public void setUp() {
		student = mock(Student.class);
		competence = new Competence("1", "Backend Development", "Spring Boot");
	}

	@Test
	public void testAddResultToStudent_Success() {
		when(studentRepository.findById("1")).thenReturn(Optional.of(student));
		when(student.addResult(competence, ResultatEnum.ACQUIRE)).thenReturn(true);
		when(studentRepository.save(any(Student.class))).thenReturn(student);

		Student updatedStudent = studentResultService.addResultToStudent("1", competence, ResultatEnum.ACQUIRE);
		assertNotNull(updatedStudent);
		verify(studentRepository, times(1)).save(student);
	}

	@Test
	public void testAddResultToStudent_StudentNotFound() {
		when(studentRepository.findById("1")).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class,
				() -> studentResultService.addResultToStudent("1", competence, ResultatEnum.ACQUIRE));
	}

	@Test
	public void testAddResultToStudent_DuplicateKeyException() {
		when(studentRepository.findById("1")).thenReturn(Optional.of(student));
		when(student.addResult(competence, ResultatEnum.ACQUIRE)).thenReturn(false);

		assertThrows(DuplicateKeyException.class,
				() -> studentResultService.addResultToStudent("1", competence, ResultatEnum.ACQUIRE));
	}

	@Test
	public void testUpdateStudentResult_Success() {
		when(studentRepository.findById("1")).thenReturn(Optional.of(student));
		when(student.updateResult(competence, ResultatEnum.FAILED)).thenReturn(true);
		when(studentRepository.save(any(Student.class))).thenReturn(student);

		Student updatedStudent = studentResultService.updateStudentResult("1", competence, ResultatEnum.FAILED);
		assertNotNull(updatedStudent);
		verify(studentRepository, times(1)).save(student);
	}

	@Test
	public void testUpdateStudentResult_StudentNotFound() {
		when(studentRepository.findById("1")).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class,
				() -> studentResultService.updateStudentResult("1", competence, ResultatEnum.FAILED));
	}

	@Test
	public void testUpdateStudentResult_NoExistingResult() {
		when(studentRepository.findById("1")).thenReturn(Optional.of(student));
		when(student.updateResult(competence, ResultatEnum.FAILED)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class,
				() -> studentResultService.updateStudentResult("1", competence, ResultatEnum.FAILED));
	}
}
