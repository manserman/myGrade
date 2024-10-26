package priv.mansour.school.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;

@Entity
public class Student extends User {
	List<NoteCompetence> competences;
	List<Project> projets;
	

	public Student(String nom, String prenom) {
		super(nom, prenom, Role.STUDENT);
		projets= new ArrayList<Project>();
		
	}
	
	public boolean addFormation(Project projet) {
		if(projets.contains(projet))
			return false;
		projets.add(projet);
		return true;
		
	}

}
