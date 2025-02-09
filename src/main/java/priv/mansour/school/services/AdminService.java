package priv.mansour.school.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import priv.mansour.school.entity.Admin;
import priv.mansour.school.exceptions.ResourceNotFoundException;
import priv.mansour.school.logger.GlobalLogger;
import priv.mansour.school.repository.AdminRepository;
import static priv.mansour.school.utils.Constants.ADMIN;

@Service
@Validated
public class AdminService {

	private final AdminRepository adminRepository;

	@Autowired
	public AdminService(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}

	public Admin addAdmin(@Valid Admin admin) {
		GlobalLogger.infoAction("Saving", ADMIN, admin);
		Admin savedAdmin = adminRepository.save(admin);
		GlobalLogger.infoSuccess("Saved", ADMIN, savedAdmin);
		return savedAdmin;
	}

	public List<Admin> getAllAdmins() {
		GlobalLogger.infoAction("Fetching all", ADMIN, "Retrieving all admins from database");
		List<Admin> admins = adminRepository.findAll();
		GlobalLogger.infoSuccess("Fetched all", ADMIN, admins.size() + " admins found");
		return admins;
	}

	public Admin getAdminById(@NotBlank String id) {
		GlobalLogger.infoAction("Fetching", ADMIN, "ID: " + id);
		return adminRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("READ", ADMIN,    id));
	}

	public Admin updateAdmin(@NotBlank String id, @Valid Admin updatedAdmin) {
		GlobalLogger.infoAction("Updating", ADMIN, "ID: " + id);
		Admin admin = adminRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("UPDATE",ADMIN,  id));

		admin.setNom(updatedAdmin.getNom());
		admin.setPrenom(updatedAdmin.getPrenom());

		Admin updated = adminRepository.save(admin);
		GlobalLogger.infoSuccess("Updated", ADMIN, id);
		return updated;
	}

	public void deleteAdminById(@NotBlank String id) {
		GlobalLogger.infoAction("Deleting", ADMIN, "ID: " + id);

		if (!adminRepository.existsById(id)) {
			throw new ResourceNotFoundException(ADMIN, "DELETE", "Admin not found for ID: " + id);
		}

		adminRepository.deleteById(id);
		GlobalLogger.infoSuccess("Deleted", ADMIN, id);
	}
}
