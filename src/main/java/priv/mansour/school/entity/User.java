package priv.mansour.school.entity;

import jakarta.persistence.Entity;

@Entity
public abstract class User {

	private String nom;
	private String prenom;
	Role role;
	public User(String nom, String prenom, Role role) {
		this.nom=nom;
		this.prenom=prenom;
		this.role=role;
	}
}
