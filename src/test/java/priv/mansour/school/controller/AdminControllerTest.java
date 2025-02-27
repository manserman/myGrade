package priv.mansour.school.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import priv.mansour.school.entity.Admin;
import priv.mansour.school.exceptions.GlobalExceptionHandler;
import priv.mansour.school.exceptions.ResourceNotFoundException;
import priv.mansour.school.services.AdminServiceImpl;
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

		mockMvc.perform(post("/admins/new")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(admin)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.nom").value("John"))
				.andExpect(jsonPath("$.prenom").value("Doe"));
	}

	@Test
	void testGetAllAdmins() throws Exception {
		List<Admin> admins = Arrays.asList(admin,
				new Admin("Jane", "Doe", "jane.doe@example.com", "password"));
		when(adminService.getAll()).thenReturn(admins);

		mockMvc.perform(get("/admins"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].nom").value("John"))
				.andExpect(jsonPath("$[1].nom").value("Jane"));
	}

	@Test
	void testGetAdminById() throws Exception {
		when(adminService.getById("1")).thenReturn(admin);

		mockMvc.perform(get("/admins/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nom").value("John"))
				.andExpect(jsonPath("$.prenom").value("Doe"));
	}

	@Test
	void testGetAdminById_NotFound() throws Exception {
		when(adminService.getById("99")).thenThrow(new ResourceNotFoundException("READ", "ADMIN", "99"));

		mockMvc.perform(get("/admins/99"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.error").value("Resource Not Found"))
				.andExpect(jsonPath("$.message").value("READ"));
	}
	@Test
	void testUpdateAdmin() throws Exception {
		Admin updatedAdmin = admin;
		when(adminService.update(eq("1"), any(Admin.class))).thenReturn(updatedAdmin);

		mockMvc.perform(put("/admins/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedAdmin)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nom").value("John"))
				.andExpect(jsonPath("$.prenom").value("Doe"))
				.andExpect(jsonPath("$.mail").value("john.updated@example.com"));
	}

	@Test
	void testDeleteAdminById() throws Exception {
		doNothing().when(adminService).deleteById("1");

		mockMvc.perform(delete("/admins/1"))
				.andExpect(status().isNoContent());
	}

	@Test
	void testDeleteAdminById_NotFound() throws Exception {
		doThrow(new ResourceNotFoundException("DELETE", "ADMIN", "99")).when(adminService).deleteById("99");

		mockMvc.perform(delete("/admins/99"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.error").value("Resource Not Found"))
				.andExpect(jsonPath("$.message").value("DELETE"));
	}
}
