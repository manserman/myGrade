package priv.mansour.school.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import priv.mansour.school.enums.ResultatEnum;
import priv.mansour.school.enums.Role;

@Document
public class Student extends User {

	private Set<CompetenceResult> results = new HashSet<>();
	@DBRef
	private Set<Project> projets = new HashSet<>();

	public Student(String nom, String prenom, String email) {
		super(nom, prenom, Role.STUDENT, email);

	}

	public boolean addProject(Project projet) {
		return projets.add(projet);

	}

	public boolean addResult(Competence competence, ResultatEnum resultat) {

		return getResults().add(new CompetenceResult(competence, resultat));

	}

	public boolean updateResult(Competence competence, ResultatEnum resultat) {
		return getResults().stream().filter(cr -> cr.getCompetence().equals(competence)).findFirst().map(cr -> {
			results.remove(cr);
			results.add(new CompetenceResult(competence, resultat));
			return true;
		}).orElse(false);
	}

	public Set<CompetenceResult> getResults() {
		return results;
	}

	public void setResults(Set<CompetenceResult> results) {
		this.results = results;
	}

}
