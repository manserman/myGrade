package priv.mansour.school.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import priv.mansour.school.entity.Admin;
import priv.mansour.school.exceptions.ResourceNotFoundException;
import priv.mansour.school.logger.GlobalLogger;
import priv.mansour.school.repository.AdminRepository;

import static priv.mansour.school.utils.Constants.ADMIN;

@Service
@Validated
public class AdminServiceImpl implements IAdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin add(Admin admin) {
        GlobalLogger.infoAction("Saving", ADMIN, admin);
        Admin savedAdmin = adminRepository.save(admin);
        GlobalLogger.infoSuccess("Saved", ADMIN, savedAdmin);
        return savedAdmin;
    }

    @Override
    public List<Admin> getAll() {
        GlobalLogger.infoAction("Fetching all", ADMIN, "Retrieving all admins from database");
        List<Admin> admins = adminRepository.findAll();
        GlobalLogger.infoSuccess("Fetched all", ADMIN, admins.size() + " admins found");
        return admins;
    }

    @Override
    public Admin getById(String id) {
        GlobalLogger.infoAction("Fetching", ADMIN, "ID: " + id);
        return adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("READ", ADMIN, id));
    }

    @Override
    public Admin update(String id, Admin updatedAdmin) {
        GlobalLogger.infoAction("Updating", ADMIN, "ID: " + id);
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UPDATE", ADMIN, id));

        admin.setNom(updatedAdmin.getNom());
        admin.setPrenom(updatedAdmin.getPrenom());

        Admin updated = adminRepository.save(admin);
        GlobalLogger.infoSuccess("Updated", ADMIN, id);
        return updated;
    }

    @Override
    public void deleteById(String id) {
        GlobalLogger.infoAction("Deleting", ADMIN, "ID: " + id);

        if (!adminRepository.existsById(id)) {
            throw new ResourceNotFoundException(ADMIN, "DELETE", "Admin not found for ID: " + id);
        }

        adminRepository.deleteById(id);
        GlobalLogger.infoSuccess("Deleted", ADMIN, id);
    }

    @Override
    public Admin findByEmail(String email) {
        GlobalLogger.infoAction("Fetching", ADMIN, "Email: " + email);
        return adminRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("READ", ADMIN, email));
    }
}
