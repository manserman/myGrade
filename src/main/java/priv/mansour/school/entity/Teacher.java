package priv.mansour.school.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import priv.mansour.school.enums.Role;

@Document
@Getter
@Setter
public class Teacher extends User {

	public Teacher(String nom, String prenom,String email) {
		super(nom, prenom, Role.TEACHER,email);
	}

}
