package priv.mansour.school.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import priv.mansour.school.entity.Admin;
import priv.mansour.school.logger.GlobalLogger;
import priv.mansour.school.services.IAdminService;

import java.util.List;

import static priv.mansour.school.utils.Constants.ADMIN;

@Validated
@RestController
@RequestMapping("/admins")
public class AdminController {

	private final IAdminService adminService;

	@Autowired
	public AdminController(IAdminService adminService) {
		this.adminService = adminService;
	}

	@PostMapping
	public ResponseEntity<Admin> addAdmin(@Valid @RequestBody Admin admin) {
		GlobalLogger.infoCreate(ADMIN, "Creating new admin: " + admin.getId());
		Admin createdAdmin = adminService.add(admin);
		GlobalLogger.infoSuccess("Created", ADMIN, "Admin ID: " + createdAdmin.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(createdAdmin);
	}

	@GetMapping
	public ResponseEntity<List<Admin>> getAllAdmins() {
		GlobalLogger.infoReadAll(ADMIN);
		List<Admin> admins = adminService.getAll();
		GlobalLogger.infoSuccess("Fetched all", ADMIN, "Total admins: " + admins.size());
		return ResponseEntity.ok(admins);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Admin> getAdminById(@PathVariable @NotBlank String id) {
		GlobalLogger.infoRead(ADMIN, "Fetching admin by ID: " + id);
		Admin admin = adminService.getById(id);
		GlobalLogger.infoSuccess("Fetched by ID", ADMIN, "Admin ID: " + admin.getId());
		return ResponseEntity.ok(admin);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Admin> updateAdmin(@PathVariable @NotBlank String id, @Valid @RequestBody Admin adminToUpdate) {
		GlobalLogger.infoUpdate(ADMIN, id, adminToUpdate);
		Admin updatedAdmin = adminService.update(id, adminToUpdate);
		GlobalLogger.infoSuccess("Updated", ADMIN, "Admin ID: " + updatedAdmin.getId());
		return ResponseEntity.ok(updatedAdmin);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAdminById(@PathVariable @NotBlank String id) {
		GlobalLogger.infoDelete(ADMIN, "Deleting admin ID: " + id);
		adminService.deleteById(id);
		GlobalLogger.infoSuccess("Deleted", ADMIN, "Admin ID: " + id);
		return ResponseEntity.noContent().build();
	}
}
