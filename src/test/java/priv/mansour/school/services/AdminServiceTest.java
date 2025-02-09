package priv.mansour.school.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
	AdminService adminService;

	Admin admin1;
	Admin admin2;

	@BeforeEach
	public void setUp() {
		admin1 = new Admin("Admin1", "Num1", "admin1@gmail.com");
		admin2 = new Admin("Admin2", "Num2", "admin2@gmail.com");
	}

	@Test
	public void testAddAdmin_Succes() {
		when(adminRepository.save(any(Admin.class))).thenReturn(admin1);
		Admin savedAdmin = adminService.addAdmin(admin1);
		assertEquals("Admin1", savedAdmin.getNom());
		assertEquals("Num1", savedAdmin.getPrenom());
		assertEquals("admin1@gmail.com", savedAdmin.getMail());
		verify(adminRepository, times(1)).save(admin1);

	}

	@Test
	public void testUpdateAdmin_Success() {
		when(adminRepository.findById("1")).thenReturn(Optional.of(admin1));
		when(adminRepository.save(any(Admin.class))).thenReturn(admin1);
		Admin updatedAdmin = adminService.updateAdmin("1", admin1);
		assertEquals("Admin1", updatedAdmin.getNom());
		assertEquals("Num1", updatedAdmin.getPrenom());
		assertEquals("admin1@gmail.com", updatedAdmin.getMail());
		verify(adminRepository, times(1)).save(admin1);
		verify(adminRepository, times(1)).findById("1");

	}

	@Test
	public void testUpdateAdmin_Fail() {
		when(adminRepository.findById("1")).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> adminService.updateAdmin("1", admin1));
		verify(adminRepository, times(0)).save(admin1);
	}

}
