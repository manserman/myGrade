package priv.mansour.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    @BeforeEach
    void setUp() throws Exception {

        project1.setAnnee("2024");
        project1.setId("1");
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
                        .content(project1Json))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertInstanceOf(MethodArgumentNotValidException.class, mvcResult.getResolvedException());
        verify(projectService, times(0)).add(project1);
    }

    @Test
    void addProjectFail_Annee_Blank() throws Exception {
        project1.setAnnee("");
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
    void addProjectFail_Annee_UnderFour() throws Exception {
        project1.setAnnee("");
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
    void addProjectFail_Annee_OverFour() throws Exception {
        project1.setAnnee("");
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
    void addProjectFail_Annee_NonNumbers() throws Exception {
        project1.setAnnee("Annee");
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
    void addProjectFail_Libelle_Blank() throws Exception {
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
    void addProjectFail_Promotion_Blank() throws Exception {
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
    void addProjectFail_Description_Blank() throws Exception {
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
    void getAllProjects_WithData() throws Exception {
        String listProjects = objectMapper.writeValueAsString(Arrays.asList(project1, project2));
        when(projectService.getAll()).thenReturn(Arrays.asList(project1, project2));
        mvcResult = mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(listProjects, mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getAllProjects_WithoutData() throws Exception {
        when(projectService.getAll()).thenReturn(new ArrayList<>());
        mvcResult = mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("[]", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getProjectById_Success() throws Exception {
        when(projectService.getById(eq("1"))).thenReturn(project1);
        mvcResult = mockMvc.perform(get("/projects/1"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(project1Json, mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getProjectById_NotFound() throws Exception {
        when(projectService.getById(eq("1"))).thenThrow(new ResourceNotFoundException("Project not found"));
        mvcResult = mockMvc.perform(get("/projects/1"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertInstanceOf(ResourceNotFoundException.class, mvcResult.getResolvedException());
        assertEquals("Project not found", mvcResult.getResolvedException().getMessage());
    }

    @Test
    void updateProject_Success() throws Exception {
        when(projectService.update(eq("1"), any(Project.class))).thenReturn(project1);
        mvcResult = mockMvc.perform(put("/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(project1Json))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(project1Json, mvcResult.getResponse().getContentAsString());
    }

    @Test
    void updateProject_NotFound() throws Exception {
        when(projectService.update(eq("1"), any(Project.class))).thenThrow(new ResourceNotFoundException("Project not found"));
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
        doNothing().when(projectService).deleteById(eq("1"));
        mockMvc.perform(delete("/projects/1"))
                .andExpect(status().isNoContent());

        verify(projectService, times(1)).deleteById("1");
    }

    @Test
    void deleteProjectById_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Project not found")).when(projectService).deleteById(eq("1"));
        mvcResult = mockMvc.perform(delete("/projects/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Resource Not Found"))
                .andExpect(jsonPath("$.message").value("Project not found"))
                .andReturn();
        assertInstanceOf(ResourceNotFoundException.class, mvcResult.getResolvedException());
        verify(projectService, times(1)).deleteById("1");
    }

    @Test
    void addCompetenceToProject_Success() throws Exception {
        Competence competence1 = new Competence("1", "Premiere competence", "comp1");
        project1.addCompetence(competence1);
        project1Json = objectMapper.writeValueAsString(project1);
        when(projectService.addCompetenceToProject(eq("1"), any(Competence.class))).thenReturn(project1);
        mockMvc.perform(post("/projects/1/competences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(project1Json)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(project1Json));
        verify(projectService, times(1)).addCompetenceToProject(eq("1"), any(Competence.class));
    }

    @Test
    void addCompetenceToProject_NotFound() throws Exception {
        Competence competence1 = new Competence("1", "Premiere competence", "comp1");
        project1.addCompetence(competence1);
        when(projectService.addCompetenceToProject(eq("1"), any(Competence.class))).thenThrow(new ResourceNotFoundException("Project not found"));
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
