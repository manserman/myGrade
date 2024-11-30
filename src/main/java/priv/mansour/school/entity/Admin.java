package priv.mansour.school.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document

public class Admin extends User {

	public Admin(String nom, String prenom) {
		super(nom, prenom, Role.ADMIN);

	}

}
