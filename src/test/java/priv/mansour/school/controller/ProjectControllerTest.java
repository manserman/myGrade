package priv.mansour.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import priv.mansour.school.entity.Competence;
import priv.mansour.school.entity.Project;
import priv.mansour.school.exceptions.DuplicateKeyException;
import priv.mansour.school.exceptions.ResourceNotFoundException;
import priv.mansour.school.services.ProjectServiceImpl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ProjectController.class)
@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

    @MockBean
    private ProjectServiceImpl projectService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Project project1 = new Project();
    private final Project project2 = new Project();
    private String project1Json;
    private MvcResult mvcResult;
    private final String PROJECT_ID = "1";

    @BeforeEach
    void setUp() throws Exception {

        project1.setAnnee("2024");
        project1.setId(PROJECT_ID);
        project1.setLibelle("Project1");
        project1.setDescription("Premiere competence");
        project1.setPromotion("2024");

        project2.setAnnee("2024");
        project2.setId("2");
        project2.setLibelle("Project2");
        project2.setDescription("Seconde competence");
        project2.setPromotion("2024");

        project1Json = objectMapper.writeValueAsString(project1);
    }

    @Test
    void addProject_Success() throws Exception {
        when(projectService.add(any(Project.class))).thenReturn(project1);
        mvcResult = mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(project1Json)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isCreated())
                .andReturn();
        assertEquals(project1Json, mvcResult.getResponse().getContentAsString());
    }

    @Test
    void addProject_Duplicate() throws Exception {
        when(projectService.add(any(Project.class))).thenThrow(new DuplicateKeyException("Already in Base"));
        mvcResult = mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(project1Json))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Duplicate Key"))
                .andExpect(jsonPath("$.message").value("Already in Base"))
                .andReturn();
        assertInstanceOf(DuplicateKeyException.class, mvcResult.getResolvedException());
    }

    @Test
    void addProjectFail_ALLFields_Blank() throws Exception {
        project1 = new Project();
        project1Json = objectMapper.writeValueAsString(project1);
        when(projectService.add(any(Project.class))).thenReturn(new Project());
        mvcResult = mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(project1Json)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.annee").value("L'annee est obligatoire."))
                .andExpect(jsonPath("$.promotion").value("Veuillez fournir une promotion pour le projet."))
                .andExpect(jsonPath("$.description").value("Veuillez fournir une description pour le projet."))
                .andExpect(jsonPath("$.libelle").value("Veuillez fournir un libelle pour le projet."))
                .andReturn();
        assertInstanceOf(MethodArgumentNotValidException.class, mvcResult.getResolvedException());
        verify(projectService, times(0)).add(project1);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "200", "20024", "Annee"})
    void test_Add_Annee_Not_VALID(String annee) throws Exception {
        project1.setAnnee(annee);
        project1Json = objectMapper.writeValueAsString(project1);
        when(projectService.add(any(Project.class))).thenReturn(new Project());
        mvcResult = mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(project1Json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.annee").value(
                        Matchers.is("L'annee doit etre au format YYYY (ex: 2024).")
                ))
                .andReturn();
        assertInstanceOf(MethodArgumentNotValidException.class, mvcResult.getResolvedException());
        verify(projectService, times(0)).add(project1);
    }


    @Test
    void testAddProjectFail_Libelle_Blank() throws Exception {
        project1.setLibelle("");
        project1Json = objectMapper.writeValueAsString(project1);
        when(projectService.add(any(Project.class))).thenReturn(new Project());
        mvcResult = mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(project1Json))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertInstanceOf(MethodArgumentNotValidException.class, mvcResult.getResolvedException());
        verify(projectService, times(0)).add(project1);
    }

    @Test
    void testAddProjectFail_Promotion_Blank() throws Exception {
        project1.setPromotion("");
        project1Json = objectMapper.writeValueAsString(project1);
        when(projectService.add(any(Project.class))).thenReturn(new Project());
        mvcResult = mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(project1Json))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertInstanceOf(MethodArgumentNotValidException.class, mvcResult.getResolvedException());
        verify(projectService, times(0)).add(project1);
    }

    @Test
    void testAddProjectFail_Description_Blank() throws Exception {
        project1.setDescription("");
        project1Json = objectMapper.writeValueAsString(project1);
        when(projectService.add(any(Project.class))).thenReturn(new Project());
        mvcResult = mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(project1Json))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertInstanceOf(MethodArgumentNotValidException.class, mvcResult.getResolvedException());
        verify(projectService, times(0)).add(project1);
    }


    @Test
    void testGetAllProjects_WithData() throws Exception {
        String listProjects = objectMapper.writeValueAsString(Arrays.asList(project1, project2));
        when(projectService.getAll()).thenReturn(Arrays.asList(project1, project2));
        mvcResult = mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(listProjects, mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testGetAllProjects_WithoutData() throws Exception {
        when(projectService.getAll()).thenReturn(new ArrayList<>());
        mvcResult = mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("[]", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testGetProjectById_Success() throws Exception {
        when(projectService.getById(PROJECT_ID)).thenReturn(project1);
        mvcResult = mockMvc.perform(get("/projects/1"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(project1Json, mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testGetProjectById_NotFound() throws Exception {
        when(projectService.getById(PROJECT_ID)).thenThrow(new ResourceNotFoundException("Project not found"));
        mvcResult = mockMvc.perform(get("/projects/1"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertInstanceOf(ResourceNotFoundException.class, mvcResult.getResolvedException());
        assertEquals("Project not found", mvcResult.getResolvedException().getMessage());
    }

    @Test
    void testUpdateProject_Success() throws Exception {
        when(projectService.update(eq(PROJECT_ID), any(Project.class))).thenReturn(project1);
        mvcResult = mockMvc.perform(put("/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(project1Json))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(project1Json, mvcResult.getResponse().getContentAsString());
    }

    @Test
    void updateProject_NotFound() throws Exception {
        when(projectService.update(eq(PROJECT_ID), any(Project.class))).thenThrow(new ResourceNotFoundException("Project not found"));
        mvcResult = mockMvc.perform(put("/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(project1Json)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isNotFound())
                .andReturn();
        assertInstanceOf(ResourceNotFoundException.class, mvcResult.getResolvedException());
        assertEquals("Project not found", mvcResult.getResolvedException().getMessage());
    }

    @Test
    void deleteProjectById_Success() throws Exception {
        doNothing().when(projectService).deleteById(PROJECT_ID);
        mockMvc.perform(delete("/projects/1"))
                .andExpect(status().isNoContent());

        verify(projectService, times(1)).deleteById(PROJECT_ID);
    }

    @Test
    void deleteProjectById_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Project not found")).when(projectService).deleteById(PROJECT_ID);
        mvcResult = mockMvc.perform(delete("/projects/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Resource Not Found"))
                .andExpect(jsonPath("$.message").value("Project not found"))
                .andReturn();
        assertInstanceOf(ResourceNotFoundException.class, mvcResult.getResolvedException());
        verify(projectService, times(1)).deleteById(PROJECT_ID);
    }

    @Test
    void addCompetenceToProject_Success() throws Exception {
        Competence competence1 = new Competence(PROJECT_ID, "Premiere competence", "comp1");
        project1.addCompetence(competence1);
        project1Json = objectMapper.writeValueAsString(project1);
        when(projectService.addCompetenceToProject(eq(PROJECT_ID), any(Competence.class))).thenReturn(project1);
        mockMvc.perform(post("/projects/1/competences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(project1Json)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(project1Json));
        verify(projectService, times(1)).addCompetenceToProject(eq(PROJECT_ID), any(Competence.class));
    }

    @Test
    void addCompetenceToProject_NotFound() throws Exception {
        Competence competence1 = new Competence(PROJECT_ID, "Premiere competence", "comp1");
        project1.addCompetence(competence1);
        when(projectService.addCompetenceToProject(eq(PROJECT_ID), any(Competence.class))).thenThrow(new ResourceNotFoundException("Project not found"));
        mvcResult = mockMvc.perform(post("/projects/1/competences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(project1Json)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Resource Not Found"))
                .andExpect(jsonPath("$.message").value("Project not found"))
                .andReturn();

        assertInstanceOf(ResourceNotFoundException.class, mvcResult.getResolvedException());
    }

}
