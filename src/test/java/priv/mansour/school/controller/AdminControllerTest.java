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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import priv.mansour.school.entity.Admin;
import priv.mansour.school.exceptions.ResourceNotFoundException;
import priv.mansour.school.services.AdminService;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

	private MockMvc mockMvc;

	@Mock
	private AdminService adminService;

	@InjectMocks
	private AdminController adminController;

	private ObjectMapper objectMapper = new ObjectMapper();
	Admin admin;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
		admin= new Admin("John", "Doe Updated", "john.updated@example.com", "password");
	}

	@Test
	void testAddAdmin() throws Exception {
		when(adminService.addAdmin(any(Admin.class))).thenReturn(admin);

		mockMvc.perform(post("/admins/new").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(admin))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.nom").value("John")).andExpect(jsonPath("$.prenom").value("Doe"));
	}

	@Test
	void testGetAllAdmins() throws Exception {
		List<Admin> admins = Arrays.asList(admin,
				new Admin("2", "Jane Doe", "jane.doe@example.com","password"));
		when(adminService.getAllAdmins()).thenReturn(admins);

		mockMvc.perform(get("/admins")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value("1"))
				.andExpect(jsonPath("$[1].id").value("2"));
	}

	@Test
	void testGetAdminById() throws Exception {
		
		when(adminService.getAdminById("1")).thenReturn(admin);

		mockMvc.perform(get("/admins/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value("1"))
				.andExpect(jsonPath("$.name").value("John Doe"));
	}

	@Test
	void testGetAdminById_NotFound() throws Exception {
		when(adminService.getAdminById("99")).thenThrow(new ResourceNotFoundException("READ", "ADMIN", "99"));

		mockMvc.perform(get("/admins/99")).andExpect(status().isNotFound());
	}

	@Test
	void testUpdateAdmin() throws Exception {
		Admin updatedAdmin = admin;
		when(adminService.updateAdmin(eq("1"), any(Admin.class))).thenReturn(updatedAdmin);

		mockMvc.perform(put("/admins/1").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatedAdmin))).andExpect(status().isOk())
				.andExpect(jsonPath("$.nom").value("John"))
				.andExpect(jsonPath("$.prenom").value("Doe Updated"))
				.andExpect(jsonPath("$.mail").value("john.updated@example.com"));
	}
	

	@Test
	void testDeleteAdminById() throws Exception {
		doNothing().when(adminService).deleteAdminById("1");

		mockMvc.perform(delete("/admins/1")).andExpect(status().isNoContent());
	}

	@Test
	void testDeleteAdminById_NotFound() throws Exception {
		doThrow(new ResourceNotFoundException("DELETE", "ADMIN", "99")).when(adminService).deleteAdminById("99");

		mockMvc.perform(delete("/admins/99")).andExpect(status().isNotFound());
	}
}
