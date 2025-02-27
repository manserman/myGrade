package priv.mansour.school.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;


import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import priv.mansour.school.enums.Role;

@SuppressWarnings("serial")
@Setter
@Getter
@NoArgsConstructor
public abstract class User {

	@Id
	private String id;

	@NotBlank(message = "Fournir un nom valide")
	private String nom;

	@NotBlank(message = "Fournir un prénom valide")
	private String prenom;

	@Indexed(unique = true)
	@NotBlank(message = "L'email est obligatoire")
	@Email(message = "L'email doit être valide")
	private String mail;

	@NotNull(message = "Le rôle ne peut pas être nul")
	@Field("role")
	private Role role;
	private String password;

	public User(String nom, String prenom, Role role, String mail,String password) {
		this.nom = nom;
		this.prenom = prenom;
		this.role = role;
		this.mail = mail;
		this.password=password;
	}


}
