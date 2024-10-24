package priv.mansour.school.entity;

import java.util.List;

import jakarta.persistence.Entity;

@Entity
public class Cours {
	private int id;
	private String libelle;
	private List<Competence> competences;
	
	private List<Student> students;
	
	private Teacher teacher;

}
