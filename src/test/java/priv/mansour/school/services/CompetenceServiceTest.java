package priv.mansour.school.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import priv.mansour.school.entity.Competence;
import priv.mansour.school.exceptions.ResourceNotFoundException;
import priv.mansour.school.repository.CompetenceRepository;

@ExtendWith(MockitoExtension.class) 
public class CompetenceServiceTest {

	@Mock
	private CompetenceRepository competenceRepository;

	@InjectMocks
	private CompetenceService competenceService;

	private Competence competence1;
	private Competence competence2;

	@BeforeEach
	void setUp() {
		competence1 = new Competence("1", "Backend Development", "Spring Boot");
		competence2 = new Competence("2", "Containerization","Docker" );
	}

	@Test
	void testAddCompetence() {
		when(competenceRepository.save(any(Competence.class))).thenReturn(competence1);

		Competence savedCompetence = competenceService.addCompetence(competence1);

		assertNotNull(savedCompetence);
		assertEquals("1", savedCompetence.getId());
		assertEquals("Spring Boot", savedCompetence.getLibelle());

		verify(competenceRepository, times(1)).save(competence1);
	}

	@Test
	void testGetAllCompetences() {
		when(competenceRepository.findAll()).thenReturn(Arrays.asList(competence1, competence2));

		List<Competence> competences = competenceService.getAllCompetences();

		assertEquals(2, competences.size());
		verify(competenceRepository, times(1)).findAll();
	}

	@Test
	void testGetCompetenceById_Success() {
		when(competenceRepository.findById("1")).thenReturn(Optional.of(competence1));

		Competence competence = competenceService.getCompetenceById("1");

		assertNotNull(competence);
		assertEquals("1", competence.getId());
		assertEquals("Spring Boot", competence.getLibelle());

		verify(competenceRepository, times(1)).findById("1");
	}

	@Test
	void testGetCompetenceById_NotFound() {
		when(competenceRepository.findById("3")).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> competenceService.getCompetenceById("3"));

		verify(competenceRepository, times(1)).findById("3");
	}

	@Test
	void testGetCompetenceByLibelle_Success() {
		when(competenceRepository.findByLibelle("Spring Boot")).thenReturn(Optional.of(competence1));

		Competence competence = competenceService.getCompetenceByLibelle("Spring Boot");

		assertNotNull(competence);
		assertEquals("Spring Boot", competence.getLibelle());

		verify(competenceRepository, times(1)).findByLibelle("Spring Boot");
	}

	@Test
	void testUpdateCompetence_Success() {
		when(competenceRepository.findById("1")).thenReturn(Optional.of(competence1));
		when(competenceRepository.save(any(Competence.class))).thenReturn(competence1);

		Competence updatedCompetence = competenceService.updateCompetence("1", competence1);

		assertNotNull(updatedCompetence);
		assertEquals("Spring Boot", updatedCompetence.getLibelle());

		verify(competenceRepository, times(1)).save(competence1);
	}

	@Test
	void testDeleteCompetenceById_Success() {
		when(competenceRepository.existsById("1")).thenReturn(true);
		 doNothing().when(competenceRepository).deleteById("1");

		assertDoesNotThrow(() -> competenceService.deleteCompetenceById("1"));

		verify(competenceRepository, times(1)).existsById("1");
		verify(competenceRepository, times(1)).deleteById("1");
	}

	@Test
	void testDeleteCompetenceById_NotFound() {
		when(competenceRepository.existsById("3")).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> competenceService.deleteCompetenceById("3"));

		verify(competenceRepository, times(1)).existsById("3");
		verify(competenceRepository, times(0)).deleteById("3");
	}
}
