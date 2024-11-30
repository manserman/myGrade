package priv.mansour.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import priv.mansour.school.entity.Admin;
import priv.mansour.school.services.AdminService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admins")
public class AdminController {

	private final AdminService adminService;

	@Autowired
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}

	@PostMapping("/new")
	public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin) {
		return ResponseEntity.ok(adminService.addAdmin(admin));
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<Admin>> getAllAdmins() {
		return ResponseEntity.ok(adminService.getAllAdmins());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Admin> getAdminById(@PathVariable int id) {
		Optional<Admin> admin = adminService.getAdminById(id);
		return admin.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Admin> updateAdmin(@PathVariable int id, @RequestBody Admin updatedAdmin) {
		try {
			Admin updated = adminService.updateAdmin(id, updatedAdmin);
			return ResponseEntity.ok(updated);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAdminById(@PathVariable int id) {
		adminService.deleteAdminById(id);
		return ResponseEntity.noContent().build();
	}
}
