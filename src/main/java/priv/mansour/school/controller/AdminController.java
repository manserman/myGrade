package priv.mansour.school.controller;

import static priv.mansour.school.utils.Constants.ADMIN;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import priv.mansour.school.entity.Admin;
import priv.mansour.school.logger.GlobalLogger;
import priv.mansour.school.services.AdminServiceImpl;
import priv.mansour.school.services.IAdminService;

@RestController
@RequestMapping("/admins")
public class AdminController {

	private final IAdminService  adminService;

	@Autowired
	public AdminController(IAdminService adminService) {
		this.adminService = adminService;
	}

	@PostMapping
	public ResponseEntity<Admin> addAdmin(@Valid @RequestBody Admin admin) {
		GlobalLogger.infoCreate(ADMIN, admin);
		Admin createdAdmin = adminService.add(admin);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdAdmin);

	}

	@GetMapping
	public ResponseEntity<List<Admin>> getAllAdmins() {
		GlobalLogger.infoReadAll(ADMIN);
		List<Admin> admins = adminService.getAll();
		return ResponseEntity.ok(admins);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Admin> getAdminById(@PathVariable String id) {
		GlobalLogger.infoRead(ADMIN, id);
		Admin admin = adminService.getById(id);
		return ResponseEntity.ok(admin);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Admin> updateAdmin(@PathVariable String id, @Valid @RequestBody Admin updatedAdmin) {
		GlobalLogger.infoUpdate(ADMIN, id, updatedAdmin);
		Admin updated = adminService.update(id, updatedAdmin);
		return ResponseEntity.ok(updated);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAdminById(@PathVariable String id) {
		GlobalLogger.infoDelete(ADMIN, id);
		adminService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
