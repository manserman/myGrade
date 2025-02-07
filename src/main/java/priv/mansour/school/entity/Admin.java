package priv.mansour.school.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import priv.mansour.school.enums.Role;

@Document
public class Admin extends User {

	public Admin(String nom, String prenom) {
		super(nom, prenom, Role.ADMIN);

	}

}
