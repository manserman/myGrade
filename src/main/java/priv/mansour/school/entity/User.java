package priv.mansour.school.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Min(value = 1, message = "L'ID doit être supérieur à 0")
	private Integer id;


	@NotBlank(message = "Fournir un nom valide")
	private String nom;

	@NotBlank(message = "Fournir un prénom valide")
	private String prenom;

	@NotNull(message = "Le rôle ne peut pas être nul")
	private Role role;

	public User(String nom, String prenom, Role role) {
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setRole(role);
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
}
