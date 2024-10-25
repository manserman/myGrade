package priv.mansour.school.entity;

import jakarta.persistence.Entity;

@Entity
public class Teacher extends User{

	public Teacher(String nom, String prenom) {
		super(nom, prenom, Role.TEACHER);
		// TODO Auto-generated constructor stub
	}

}
