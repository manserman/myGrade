package priv.mansour.school.entity;

import jakarta.persistence.Entity;

@Entity
public class NoteCompetence {
	private Student etudiant;
	private Competence competence;
	private String acquisition;

}
