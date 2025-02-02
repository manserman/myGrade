package priv.mansour.school.controller;

import java.util.List;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import priv.mansour.school.entity.Admin;
import priv.mansour.school.services.AdminService;

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
		return adminService.getAdminById(id).
				map(ResponseEntity::ok)
				.orElseThrow(() -> new ResourceNotFoundException("user not found"));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Admin> updateAdmin(@PathVariable int id, @RequestBody Admin updatedAdmin) {
		return ResponseEntity.ok(adminService.updateAdmin(id, updatedAdmin));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAdminById(@PathVariable int id) {
		adminService.deleteAdminById(id);
		return ResponseEntity.noContent().build();
	}
}
