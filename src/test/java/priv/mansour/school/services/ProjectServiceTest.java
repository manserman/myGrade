package priv.mansour.school.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import priv.mansour.school.entity.Competence;
import priv.mansour.school.entity.Project;
import priv.mansour.school.entity.Teacher;
import priv.mansour.school.exceptions.DuplicateKeyException;
import priv.mansour.school.exceptions.ResourceNotFoundException;
import priv.mansour.school.repository.ProjectRepository;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    
    @Mock
    ProjectRepository projectRepository;
    
    @InjectMocks
    ProjectService projectService;
    
    Project project1;
    Project project2;
    Competence competence1;
    Competence competence2;
    Teacher teacher;
    
    @BeforeEach
    public void setUp() {
        competence1 = new Competence("1", "Backend Development", "Spring Boot");
        competence2 = new Competence("2", "Containerization", "Docker");
        teacher = new Teacher("John Doe", "Mavs","john@doe.com");
        project1 = new Project("1", "2025", "MIAGE", "Project Description", "Project1", teacher, Arrays.asList(competence1, competence2));
        project2 = new Project("2", "2025", "MIAGE", "Another Description", "Project2", teacher, new ArrayList<>(Arrays.asList(competence1)));
    }
    
    @Test
    public void testAddProject_Success() {
        when(projectRepository.save(any(Project.class))).thenReturn(project1);
        Project savedProject = projectService.addProject(project1);
        assertEquals("Project1", savedProject.getLibelle());
        verify(projectRepository, times(1)).save(project1);
    }
    
    @Test
    public void testGetAllProjects_Success() {
        when(projectRepository.findAll()).thenReturn(Arrays.asList(project1, project2));
        List<Project> projects = projectService.getAllProjects();
        assertEquals(2, projects.size());
        verify(projectRepository, times(1)).findAll();
    }
    
    @Test
    public void testGetProjectById_Success() {
        when(projectRepository.findById("1")).thenReturn(Optional.of(project1));
        Project foundProject = projectService.getProjectById("1");
        assertEquals("Project1", foundProject.getLibelle());
        verify(projectRepository, times(1)).findById("1");
    }
    
    @Test
    public void testGetProjectById_Fail() {
        when(projectRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> projectService.getProjectById("1"));
        verify(projectRepository, times(1)).findById("1");
    }
    
    @Test
    public void testUpdateProject_Success() {
        when(projectRepository.findById("1")).thenReturn(Optional.of(project1));
        when(projectRepository.save(any(Project.class))).thenReturn(project1);
        Project updatedProject = projectService.updateProject("1", project1);
        assertEquals("Project1", updatedProject.getLibelle());
        verify(projectRepository, times(1)).save(project1);
        verify(projectRepository, times(1)).findById("1");
    }
    
    @Test
    public void testDeleteProjectById_Success() {
        when(projectRepository.existsById("1")).thenReturn(true);
        doNothing().when(projectRepository).deleteById("1");
        projectService.deleteProjectById("1");
        verify(projectRepository, times(1)).deleteById("1");
    }
    
    @Test
    public void testDeleteProjectById_Fail() {
        when(projectRepository.existsById("1")).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> projectService.deleteProjectById("1"));
        verify(projectRepository, times(0)).deleteById("1");
    }
    
    @Test
    public void testAddCompetenceToProject_Success() {
        when(projectRepository.findById("2")).thenReturn(Optional.of(project2));
        when(projectRepository.save(any(Project.class))).thenReturn(project2);
        Project updatedProject = projectService.addCompetenceToProject("2", competence2);
        assertTrue(updatedProject.getCompetences().contains(competence2));
        verify(projectRepository, times(1)).save(project2);
    }
    
    @Test
    public void testAddCompetenceToProject_Duplicate() {
        project1.setCompetences(Arrays.asList(competence1));
        when(projectRepository.findById("1")).thenReturn(Optional.of(project1));
        assertThrows(DuplicateKeyException.class, () -> projectService.addCompetenceToProject("1", competence1));
    }
}
