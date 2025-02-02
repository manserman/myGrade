package priv.mansour.school.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED) 
public abstract class User {
	
	@Nonnull
	@Id
	private int id;
	@Nonnull
	private String nom;
	@Nonnull
	private String prenom;
	@Nonnull
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
