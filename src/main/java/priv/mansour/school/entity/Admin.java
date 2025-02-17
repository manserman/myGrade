package priv.mansour.school.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import priv.mansour.school.enums.Role;

@SuppressWarnings("serial")
@Document
public class Admin extends User {

	public Admin(String nom, String prenom,String mail,String password) {
		super(nom, prenom, Role.ADMIN,mail,password);

	}

}
