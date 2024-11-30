package priv.mansour.school.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import priv.mansour.school.entity.Admin;
import priv.mansour.school.repository.AdminRepository;

@Service
public class AdminService {

	private final AdminRepository adminRepository;

	@Autowired
	public AdminService(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}

	// Ajouter un admin
	public Admin addAdmin(Admin admin) {
		return adminRepository.save(admin);
	}

	// Récupérer tous les admins
	public List<Admin> getAllAdmins() {
		return adminRepository.findAll();
	}

	// Récupérer un admin par ID
	public Optional<Admin> getAdminById(int id) {
		return adminRepository.findById(id);
	}

	// Mettre à jour un admin
	public Admin updateAdmin(int id, Admin updatedAdmin) {
		Optional<Admin> existingAdmin = adminRepository.findById(id);
		if (existingAdmin.isPresent()) {
			Admin admin = existingAdmin.get();
			admin.setNom(updatedAdmin.getNom());
			admin.setPrenom(updatedAdmin.getPrenom());
			// Ajoutez d'autres champs à mettre à jour si nécessaire
			return adminRepository.save(admin);
		}
		throw new RuntimeException("Admin non trouvé avec l'ID : " + id);
	}

	// Supprimer un admin par ID
	public void deleteAdminById(int id) {
		adminRepository.deleteById(id);
	}
}
