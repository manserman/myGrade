package priv.mansour.school.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.NoArgsConstructor;

@Document
@NoArgsConstructor
public class Admin extends User {

	public Admin(String nom, String prenom) {
		super(nom, prenom, Role.ADMIN);

	}

}
