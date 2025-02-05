package priv.mansour.school.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import priv.mansour.school.entity.Admin;
import priv.mansour.school.exceptions.ResourceNotFoundException;
import priv.mansour.school.repository.AdminRepository;

@Service
@Validated
public class AdminService {

	private final AdminRepository adminRepository;

	@Autowired
	public AdminService(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}

	public Admin addAdmin(@Valid Admin admin) {
		return adminRepository.save(admin);
	}

	public List<Admin> getAllAdmins() {
		return adminRepository.findAll();
	}

	public Admin getAdminById(@NotBlank String id) {
		Admin admin = adminRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Admin non trouvé avec l'ID : " + id));
		return admin;
	}

	public Admin updateAdmin(@NotBlank String id, Admin updatedAdmin) {
		Admin admin = adminRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Admin non trouvé avec l'ID : " + id));
		admin.setNom(updatedAdmin.getNom());
		admin.setPrenom(updatedAdmin.getPrenom());
		return adminRepository.save(admin);

	}

	public void deleteAdminById(@NotBlank String id) {
		if (!adminRepository.existsById(id)) {
			throw new ResourceNotFoundException("Admin non trouvé avec l'ID : " + id);
		}
		adminRepository.deleteById(id);
	}

}
