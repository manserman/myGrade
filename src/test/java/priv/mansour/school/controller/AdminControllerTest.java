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
import priv.mansour.school.entity.Admin;
import priv.mansour.school.exceptions.ResourceNotFoundException;
import priv.mansour.school.services.AdminServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AdminController.class)
@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminServiceImpl adminService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Admin admin;

    @BeforeEach
    void setUp() {
        admin = new Admin("John", "Doe", "john.updated@example.com", "password");
    }

    @Test
    void testAddAdmin() throws Exception {
        when(adminService.add(any(Admin.class))).thenReturn(admin);

        MvcResult result = mockMvc.perform(post("/admins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(admin)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nom").value("John"))
                .andExpect(jsonPath("$.prenom").value("Doe"))
                .andReturn();
        assertEquals(objectMapper.writeValueAsString(admin), result.getResponse().getContentAsString());
        verify(adminService, times(1)).add(admin);
    }

    @Test
    void testGetAllAdmins() throws Exception {
        List<Admin> admins = Arrays.asList(admin,
                new Admin("Jane", "Doe", "jane.doe@example.com", "password"));
        when(adminService.getAll()).thenReturn(admins);

        MvcResult mvcResult = mockMvc.perform(get("/admins"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nom").value("John"))
                .andExpect(jsonPath("$[1].nom").value("Jane"))
                .andReturn();
        assertEquals(objectMapper.writeValueAsString(admins), mvcResult.getResponse().getContentAsString());
        verify(adminService, times(1)).getAll();
    }

    @Test
    void testGetAdminById() throws Exception {
        when(adminService.getById("1")).thenReturn(admin);

        mockMvc.perform(get("/admins/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("John"))
                .andExpect(jsonPath("$.prenom").value("Doe"));
        verify(adminService, times(1)).getById("99");
    }

    @Test
    void testGetAdminById_NotFound() throws Exception {
        when(adminService.getById("99")).thenThrow(new ResourceNotFoundException("READ", "ADMIN", "99"));

        MvcResult mvcResult = mockMvc.perform(get("/admins/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Resource Not Found"))
                .andExpect(jsonPath("$.message").value("READ"))
                .andReturn();
        assertInstanceOf(ResourceNotFoundException.class, mvcResult.getResolvedException());
        verify(adminService, times(1)).getById("99");
    }

    @Test
    void testUpdateAdmin() throws Exception {

        when(adminService.update(eq("1"), any(Admin.class))).thenReturn(admin);

        mockMvc.perform(put("/admins/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(admin)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("John"))
                .andExpect(jsonPath("$.prenom").value("Doe"))
                .andExpect(jsonPath("$.mail").value("john.updated@example.com"));
        verify(adminService, times(1)).update("1", admin);

    }

    @Test
    void testDeleteAdminById() throws Exception {
        doNothing().when(adminService).deleteById("1");

        mockMvc.perform(delete("/admins/1"))
                .andExpect(status().isNoContent());
        verify(adminService, times(1)).deleteById("1");
    }

    @Test
    void testDeleteAdminById_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("DELETE", "ADMIN", "99")).when(adminService).deleteById("99");

        MvcResult mvcResult = mockMvc.perform(delete("/admins/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Resource Not Found"))
                .andExpect(jsonPath("$.message").value("DELETE"))
                .andReturn();
        assertInstanceOf(ResourceNotFoundException.class, mvcResult.getResolvedException());
        verify(adminService, times(1)).deleteById("99");
    }
}
