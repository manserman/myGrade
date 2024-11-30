package priv.mansour.school.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.NoArgsConstructor;
@NoArgsConstructor
@Document
public class Student extends User {

	Map<Competence, ResultatEnum> resultats = new HashMap<>();
	@DBRef
	List<Project> projets;

	public Student(String nom, String prenom) {
		super(nom, prenom, Role.STUDENT);
		projets = new ArrayList<Project>();

	}

	public boolean addProject(Project projet) {
		if (projets.contains(projet))
			return false;
		projets.add(projet);
		return true;

	}

	public boolean addOrUpdateResult(Competence competence, ResultatEnum resultat) {

		resultats.put(competence, resultat);
		return true;

	}

}
