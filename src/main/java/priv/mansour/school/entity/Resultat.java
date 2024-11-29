package priv.mansour.school.entity;

import jakarta.persistence.Entity;

@Entity
public class Resultat {
	private Student etudiant;
	private Competence competence;
	private ResultatEnum result;;

}
