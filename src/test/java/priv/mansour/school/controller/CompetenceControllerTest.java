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
import priv.mansour.school.entity.Competence;
import priv.mansour.school.exceptions.DuplicateKeyException;
import priv.mansour.school.exceptions.ResourceNotFoundException;
import priv.mansour.school.services.CompetenceServiceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(CompetenceController.class)
@ExtendWith(MockitoExtension.class)
class CompetenceControllerTest {
    @MockBean
    CompetenceServiceImpl competenceService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    Competence competence1, competence2;
    String comp1Json, comp2Json;
    MvcResult mvcResult;

    @BeforeEach
    void setUp() throws Exception {
        competence1 = new Competence("1", "Premiere competence", "comp1");
        competence2 = new Competence("2", "Seconde competence", "comp2");
        comp1Json = objectMapper.writeValueAsString(competence1);
        comp2Json = objectMapper.writeValueAsString(competence2);
    }

    @Test
    void addCompetence_Success() throws Exception {
        when(competenceService.add(any(Competence.class))).thenReturn(competence1);

        mvcResult = mockMvc.perform(post("/competences/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(comp1Json))
                .andExpect(status().isCreated())
                .andReturn();

        assertEquals(comp1Json, mvcResult.getResponse().getContentAsString());
    }

    @Test
    void addCompetence_Duplicate() throws Exception {
        when(competenceService.add(any(Competence.class))).thenThrow(new DuplicateKeyException("Already in Base"));
        mvcResult = mockMvc.perform(post("/competences/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(comp1Json))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Duplicate Key"))
                .andExpect(jsonPath("$.message").value("Already in Base"))
                .andReturn();

        assertInstanceOf(DuplicateKeyException.class, mvcResult.getResolvedException());
    }
    @Test
    void addCompetence_NotValid_BlankDescriptionAndLibelle() throws Exception {
        competence1 = new Competence("1", "", "");
        comp1Json = objectMapper.writeValueAsString(competence1);
        when(competenceService.add(any(Competence.class))).thenReturn(competence1);

        mockMvc.perform(post("/competences/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(comp1Json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.libelle").value("Veuillez fournir un libelle pour le projet."))
                .andExpect(jsonPath("$.description").value("Veuillez fournir une description pour le projet."));

    }


    @Test
    void addCompetence_NotValid_VoidLibelle() throws Exception {
        competence1 = new Competence("1", "Premiere competence", "");
        comp1Json = objectMapper.writeValueAsString(competence1);
        when(competenceService.add(any(Competence.class))).thenReturn(competence1);
        mockMvc.perform(post("/competences/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(comp1Json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.libelle").value("Veuillez fournir un libelle pour le projet."))
        ;
    }
    @Test
    void addCompetence_NotValid_BlankDescription() throws Exception {
        competence1 = new Competence("1", "", "comp1");
        comp1Json = objectMapper.writeValueAsString(competence1);
        when(competenceService.add(any(Competence.class))).thenReturn(competence1);
        mockMvc.perform(post("/competences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(comp1Json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.description").value("Veuillez fournir une description pour le projet."));
    }

    @Test
    void getAllCompetences_WithData() throws Exception {
        String listComps = objectMapper.writeValueAsString(Arrays.asList(competence1, competence2));
        when(competenceService.getAll()).thenReturn(Arrays.asList(competence1, competence2));
        mvcResult = mockMvc.perform(get("/competences"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(listComps, mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getAllCompetences_WithoutData() throws Exception {
        when(competenceService.getAll()).thenReturn(new ArrayList<>());
        mvcResult = mockMvc.perform(get("/competences"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("[]", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void updateCompetence_Success() throws Exception {
        when(competenceService.update(eq("1"), any(Competence.class))).thenReturn(competence1);
        mvcResult = mockMvc.perform(put("/competences/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(comp1Json))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(comp1Json, mvcResult.getResponse().getContentAsString());
    }

    @Test
    void updateCompetence_NotFound() throws Exception {
        when(competenceService.update(eq("1"), any(Competence.class))).thenThrow(new ResourceNotFoundException("Competence not found"));
        mvcResult = mockMvc.perform(put("/competences/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(comp1Json))
                .andExpect(status().isNotFound())
                .andReturn();
        Exception exception = mvcResult.getResolvedException();
        assertNotNull(exception);
        assertInstanceOf(ResourceNotFoundException.class, exception);
        assertEquals("Competence not found", exception.getMessage());
    }
    @Test
    void updateCompetence_NotValid_BlankDescriptionAndLibelle() throws Exception {
        competence1 = new Competence("1", "", "");
        comp1Json = objectMapper.writeValueAsString(competence1);
        when(competenceService.add(any(Competence.class))).thenReturn(competence1);

        mockMvc.perform(put("/competences/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(comp1Json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.libelle").value("Veuillez fournir un libelle pour le projet."))
                .andExpect(jsonPath("$.description").value("Veuillez fournir une description pour le projet."));

    }


    @Test
    void updateCompetence_NotValid_VoidLibelle() throws Exception {
        competence1 = new Competence("1", "Premiere competence", "");
        comp1Json = objectMapper.writeValueAsString(competence1);
        when(competenceService.add(any(Competence.class))).thenReturn(competence1);
        mockMvc.perform(put("/competences/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(comp1Json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.libelle").value("Veuillez fournir un libelle pour le projet."))
        ;
    }
    @Test
    void updateCompetence_NotValid_BlankDescription() throws Exception {
        competence1 = new Competence("1", "", "comp1");
        comp1Json = objectMapper.writeValueAsString(competence1);
        when(competenceService.add(any(Competence.class))).thenReturn(competence1);
        mockMvc.perform(put("/competences/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(comp1Json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.description").value("Veuillez fournir une description pour le projet."));
    }

    @Test
    void deleteCompetenceById_Success() throws Exception {
        doNothing().when(competenceService).deleteById(eq("1"));
        mockMvc.perform(delete("/competences/1"))
                .andExpect(status().isNoContent());
        verify(competenceService, times(1)).deleteById("1");
    }

    @Test
    void deleteCompetenceById_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Competence not found")).when(competenceService).deleteById(eq("1"));
        mvcResult = mockMvc.perform(delete("/competences/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Resource Not Found"))
                .andExpect(jsonPath("$.message").value("Competence not found"))
                .andReturn();

        assertInstanceOf(ResourceNotFoundException.class, mvcResult.getResolvedException());
        verify(competenceService, times(1)).deleteById("1");
    }
}
