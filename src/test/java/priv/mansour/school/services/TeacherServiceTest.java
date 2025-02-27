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

import priv.mansour.school.entity.Teacher;
import priv.mansour.school.exceptions.ResourceNotFoundException;
import priv.mansour.school.repository.TeacherRepository;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

	@Mock
	TeacherRepository teacherRepository;

	@InjectMocks
	TeacherServiceImpl teacherService;

	Teacher teacher1;
	Teacher teacher2;

	@BeforeEach
	public void setUp() {
		teacher1 = new Teacher("John", "Doe", "john.doe@example.com", "Password1");
		teacher2 = new Teacher("Jane", "Smith", "jane.smith@example.com", "Password1");
	}

	@Test
	public void testAddTeacher_Success() {
		when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher1);
		Teacher savedTeacher = teacherService.add(teacher1);
		assertEquals("John", savedTeacher.getNom());
		verify(teacherRepository, times(1)).save(teacher1);
	}

	@Test
	public void testGetAllTeachers_Success() {
		when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher1, teacher2));
		List<Teacher> teachers = teacherService.getAll();
		assertEquals(2, teachers.size());
		verify(teacherRepository, times(1)).findAll();
	}

	@Test
	public void testGetTeacherById_Success() {
		when(teacherRepository.findById("1")).thenReturn(Optional.of(teacher1));
		Teacher foundTeacher = teacherService.getById("1");
		assertEquals("John", foundTeacher.getNom());
		verify(teacherRepository, times(1)).findById("1");
	}

	@Test
	public void testGetTeacherById_Fail() {
		when(teacherRepository.findById("1")).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> teacherService.getById("1"));
		verify(teacherRepository, times(1)).findById("1");
	}

	@Test
	public void testDeleteTeacherById_Success() {
		when(teacherRepository.existsById("1")).thenReturn(true);
		doNothing().when(teacherRepository).deleteById("1");
		teacherService.deleteById("1");
		verify(teacherRepository, times(1)).deleteById("1");
	}

	@Test
	public void testDeleteTeacherById_Fail() {
		when(teacherRepository.existsById("1")).thenReturn(false);
		assertThrows(ResourceNotFoundException.class, () -> teacherService.deleteById("1"));
		verify(teacherRepository, times(0)).deleteById("1");
	}
}
