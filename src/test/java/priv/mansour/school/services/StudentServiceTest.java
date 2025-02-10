package priv.mansour.school.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import priv.mansour.school.entity.Student;
import priv.mansour.school.exceptions.ResourceNotFoundException;
import priv.mansour.school.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

	@Mock
	StudentRepository studentRepository;

	@InjectMocks
	StudentService studentService;

	Student student1;
	Student student2;

	@BeforeEach
	public void setUp() {
		student1 = new Student("John", "Doe", "john.doe@example.com");
		student2 = new Student("Jane", "Smith", "jane.smith@example.com");
	}

	@Test
	public void testAddStudent_Success() {
		when(studentRepository.save(any(Student.class))).thenReturn(student1);
		Student savedStudent = studentService.addStudent(student1);
		assertEquals("John", savedStudent.getNom());
		verify(studentRepository, times(1)).save(student1);
	}

	@Test
	public void testGetAllStudents_Success() {
		when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student2));
		List<Student> students = studentService.getAllStudents();
		assertEquals(2, students.size());
		verify(studentRepository, times(1)).findAll();
	}

	@Test
	public void testGetStudentById_Success() {
		when(studentRepository.findById("1")).thenReturn(Optional.of(student1));
		Student foundStudent = studentService.getStudentById("1");
		assertEquals("John", foundStudent.getNom());
		verify(studentRepository, times(1)).findById("1");
	}

	@Test
	public void testGetStudentById_Fail() {
		when(studentRepository.findById("1")).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> studentService.getStudentById("1"));
		verify(studentRepository, times(1)).findById("1");
	}

	@Test
	public void testUpdateStudent_Success() {
		when(studentRepository.findById("1")).thenReturn(Optional.of(student1));
		when(studentRepository.save(any(Student.class))).thenReturn(student1);
		Student updatedStudent = studentService.updateStudent("1", student1);
		assertEquals("John", updatedStudent.getNom());
		verify(studentRepository, times(1)).save(student1);
		verify(studentRepository, times(1)).findById("1");
	}

	@Test
	public void testDeleteStudentById_Success() {
		when(studentRepository.existsById("1")).thenReturn(true);
		doNothing().when(studentRepository).deleteById("1");
		studentService.deleteStudentById("1");
		verify(studentRepository, times(1)).deleteById("1");
	}

	@Test
	public void testDeleteStudentById_Fail() {
		when(studentRepository.existsById("1")).thenReturn(false);
		assertThrows(ResourceNotFoundException.class, () -> studentService.deleteStudentById("1"));
		verify(studentRepository, times(0)).deleteById("1");
	}
}
