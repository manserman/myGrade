package priv.mansour.school.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import priv.mansour.school.entity.Admin;
import priv.mansour.school.exceptions.ResourceNotFoundException;
import priv.mansour.school.repository.AdminRepository;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

	@Mock
	AdminRepository adminRepository;

	@InjectMocks
	AdminServiceImpl adminService;

	Admin admin1;
	Admin admin2;

	@BeforeEach
	public void setUp() {
		admin1 = new Admin("Admin1", "Num1", "admin1@gmail.com", "password1");
		admin2 = new Admin("Admin2", "Num2", "admin2@gmail.com", "password2");
	}

	@Test
	public void testAddAdmin_Success() {
		when(adminRepository.save(any(Admin.class))).thenReturn(admin1);
		Admin savedAdmin = adminService.add(admin1);
		assertEquals("Admin1", savedAdmin.getNom());
		assertEquals("admin1@gmail.com", savedAdmin.getMail());
		verify(adminRepository, times(1)).save(admin1);
	}

	@Test
	public void testGetAllAdmins_Success() {
		when(adminRepository.findAll()).thenReturn(Arrays.asList(admin1, admin2));
		List<Admin> admins = adminService.getAll();
		assertEquals(2, admins.size());
		verify(adminRepository, times(1)).findAll();
	}

	@Test
	public void testGetAdminById_Success() {
		when(adminRepository.findById("1")).thenReturn(Optional.of(admin1));
		Admin foundAdmin = adminService.getById("1");
		assertEquals("Admin1", foundAdmin.getNom());
		assertEquals("admin1@gmail.com", foundAdmin.getMail());
		verify(adminRepository, times(1)).findById("1");
	}

	@Test
	public void testGetAdminById_Fail() {
		when(adminRepository.findById("1")).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> adminService.getById("1"));
		verify(adminRepository, times(1)).findById("1");
	}

	@Test
	public void testUpdateAdmin_Success() {
		when(adminRepository.findById("1")).thenReturn(Optional.of(admin1));
		when(adminRepository.save(any(Admin.class))).thenReturn(admin1);
		Admin updatedAdmin = adminService.update("1", admin1);
		assertEquals("Admin1", updatedAdmin.getNom());
		assertEquals("admin1@gmail.com", updatedAdmin.getMail());
		verify(adminRepository, times(1)).save(admin1);
		verify(adminRepository, times(1)).findById("1");
	}

	@Test
	public void testUpdateAdmin_Fail() {
		when(adminRepository.findById("1")).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> adminService.update("1", admin1));
		verify(adminRepository, times(0)).save(admin1);
	}

	@Test
	public void testDeleteAdminById_Success() {
		when(adminRepository.existsById("1")).thenReturn(true);
		doNothing().when(adminRepository).deleteById("1");
		adminService.deleteById("1");
		verify(adminRepository, times(1)).deleteById("1");
	}

	@Test
	public void testDeleteAdminById_Fail() {
		when(adminRepository.existsById("1")).thenReturn(false);
		assertThrows(ResourceNotFoundException.class, () -> adminService.deleteById("1"));
		verify(adminRepository, times(0)).deleteById("1");
	}
}
