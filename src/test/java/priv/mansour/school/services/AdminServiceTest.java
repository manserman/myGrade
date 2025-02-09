package priv.mansour.school.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import priv.mansour.school.entity.Admin;
import priv.mansour.school.repository.AdminRepository;
@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
	@Mock
	AdminRepository adminrepository;
	
	@InjectMocks
	AdminService adminService;
	
	Admin admin1;
	Admin admin2;
	@BeforeEach
	public void setUp() {
		admin1= new Admin("Admin1","Num1", "admin1@gmail.com");
		admin2= new Admin("Admin2","Num2", "admin2@gmail.com");
	}
	@Test
	public void testAddAdmin_Succes() {
		when(adminrepository.save(any(Admin.class))).thenReturn(admin1);
		Admin savedAdmin= adminService.addAdmin(admin1);
		assertEquals("Admin1", savedAdmin.getNom());
		assertEquals("Num1", savedAdmin.getPrenom());
		assertEquals("admin1@gmail.com", savedAdmin.getMail());
		verify(adminrepository,  times(1)).save(admin1);
		
	}

}
