package priv.mansour.school.entity;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import priv.mansour.school.enums.Role;

@Document
@Getter
@Setter
public class Teacher extends User {
	@DBRef
	private Set<Project> projets;

	public Teacher(String nom, String prenom) {
		super(nom, prenom, Role.TEACHER);
	}

}
